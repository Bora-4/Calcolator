package com.calorator.repository.impl;

import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.repository.CalorieThresholdRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class CalorieThresholdRepositoryImpl implements CalorieThresholdRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CalorieThresholdEntity findByUserIdAndDate(Long userId, Date date) {
        try {
            return entityManager.createQuery("FROM CalorieThresholdEntity WHERE user.id = :userId AND thresholdDate = :date", CalorieThresholdEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("date", date)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public CalorieThresholdEntity save(CalorieThresholdEntity calorieThresholdEntity) {
        if (calorieThresholdEntity.getThresholdId() == 0) {
            entityManager.persist(calorieThresholdEntity);
            return calorieThresholdEntity;
        } else {
            return entityManager.merge(calorieThresholdEntity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        CalorieThresholdEntity calorieThresholdEntity = findById(id);
        if (calorieThresholdEntity != null) {
            entityManager.remove(calorieThresholdEntity);
        }
    }

    private CalorieThresholdEntity findById(Long id) {
        return entityManager.find(CalorieThresholdEntity.class, id);
    }

    @Override
    public List<Date> findExceededThresholdDates(Long userId, Date startDate, Date endDate) {
        return entityManager.createQuery("SELECT thresholdDate FROM CalorieThresholdEntity WHERE user.id = :userId AND isWarningTriggered AND thresholdDate BETWEEN :startDate AND :endDate", Date.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
