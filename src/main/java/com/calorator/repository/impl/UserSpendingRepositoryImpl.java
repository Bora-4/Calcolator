package com.calorator.repository.impl;

import com.calorator.entity.UserSpendingEntity;
import com.calorator.repository.UserSpendingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class UserSpendingRepositoryImpl implements UserSpendingRepository {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void save(UserSpendingEntity userSpendingEntity) {
        entityManager.persist(userSpendingEntity);
    }

    @Override
    public void update(UserSpendingEntity userSpendingEntity) {
        entityManager.merge(userSpendingEntity);
    }

    @Override
    public void delete(Long spendingId) {
        UserSpendingEntity entity = findById(spendingId);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public UserSpendingEntity findById(Long spendingId) {
        return entityManager.find(UserSpendingEntity.class, spendingId);
    }

    @Override
    public List<UserSpendingEntity> findAll() {
        return entityManager.createQuery("SELECT u FROM UserSpendingEntity u", UserSpendingEntity.class)
                .getResultList();
    }
}
