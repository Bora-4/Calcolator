package com.calorator.repository.impl;

import com.calorator.entity.UserSpendingEntity;
import com.calorator.repository.UserSpendingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserSpendingRepositoryImpl implements UserSpendingRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
        String query = "SELECT u FROM UserSpendingEntity u";
        return entityManager.createQuery(query, UserSpendingEntity.class).getResultList();
    }
}
