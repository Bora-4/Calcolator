package com.calorator.repository.impl;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.repository.MonthlyExpenditureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class MonthlyExpenditureRepositoryImpl implements MonthlyExpenditureRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(MonthlyExpenditureEntity monthlyExpenditure) {
        if (monthlyExpenditure.getId() == null) {
            em.persist(monthlyExpenditure);
        } else {
            em.merge(monthlyExpenditure);
        }
    }

    @Override
    public BigDecimal calculateMonthlySpending(Long userId, int month, int year) {
        try {
            String sql = """
                    SELECT COALESCE(SUM(total_spent), 0) AS total_price
                    FROM monthly_expenditure
                    WHERE user_id = :userId
                      AND MONTH(month) = :month
                      AND YEAR(month) = :year;""";
            System.out.println("Executing SQL: " + sql + " with params: userId=" + userId + ", month=" + month + ", year=" + year);
            return (BigDecimal) em.createNativeQuery(sql)
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while calculating monthly spending.", e);
        }
    }

    @Override
    public MonthlyExpenditureEntity findByUserIdAndMonth(Long userId, LocalDate month) {
        try {
            return em.createQuery(
                            "SELECT m FROM MonthlyExpenditureEntity m WHERE m.user.id = :userId AND m.month = :month",
                            MonthlyExpenditureEntity.class
                    )
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


}
