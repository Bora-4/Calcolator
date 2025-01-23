package com.calorator.repository.impl;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.repository.MonthlyExpenditureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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
            return null; // Return null if no record is found
        }
    }


}
