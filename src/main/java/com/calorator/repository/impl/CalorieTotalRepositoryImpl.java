package com.calorator.repository.impl;

import com.calorator.entity.CalorieTotalEntity;
import com.calorator.repository.CalorieTotalRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Date;

@Repository
public class CalorieTotalRepositoryImpl implements CalorieTotalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CalorieTotalEntity findByUserIdAndDate(int userId, Date date) {
        return entityManager.createQuery("FROM CalorieTotalEntity WHERE userId = :userId AND date = :date", CalorieTotalEntity.class)
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();
    }

    @Override
    @Transactional
    public CalorieTotalEntity save(CalorieTotalEntity calorieTotalEntity) {
        if (calorieTotalEntity.getId() == 0) {
            entityManager.persist(calorieTotalEntity);
            return calorieTotalEntity;
        } else {
            return entityManager.merge(calorieTotalEntity);
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        CalorieTotalEntity calorieTotalEntity = findById(id);
        if (calorieTotalEntity != null) {
            entityManager.remove(calorieTotalEntity);
        }
    }

    private CalorieTotalEntity findById(int id) {
        return entityManager.find(CalorieTotalEntity.class, id);
    }
}
