package com.calorator.repository.impl;

import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.repository.WeeklyStatisticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class WeeklyStatisticsRepositoryImpl implements WeeklyStatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(WeeklyStatisticsEntity weeklyStatisticsEntity) {
        entityManager.persist(weeklyStatisticsEntity);
    }

    @Override
    public WeeklyStatisticsEntity findById(Long id) {
        return entityManager.find(WeeklyStatisticsEntity.class, id);
    }

    @Override
    public List<WeeklyStatisticsEntity> findAll() {
        return entityManager.createQuery("SELECT w FROM WeeklyStatisticsEntity w", WeeklyStatisticsEntity.class)
                .getResultList();
    }

    @Override
    public void update(WeeklyStatisticsEntity weeklyStatisticsEntity) {
        entityManager.merge(weeklyStatisticsEntity);
    }

    @Override
    public void delete(Long id) {
        WeeklyStatisticsEntity entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public Optional<WeeklyStatisticsEntity> findByStatisticName(String statisticName) {
        return entityManager.createQuery("SELECT w FROM WeeklyStatisticsEntity w WHERE w.statisticName = :statisticName", WeeklyStatisticsEntity.class)
                .setParameter("statisticName", statisticName)
                .getResultList()
                .stream()
                .findFirst();
    }
}