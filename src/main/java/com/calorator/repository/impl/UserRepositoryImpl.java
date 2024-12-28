package com.calorator.repository.impl;

import com.calorator.entity.UserEntity;
import com.calorator.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    @Override
    public void save(UserEntity userEntity) {
        em.persist(userEntity);
    }

    @Override
    public UserEntity findById(Long id) {
        return em.find(UserEntity.class, id);
    }

    @Override
    public UserEntity findByName(String name) {
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.name = :name", UserEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void update(UserEntity userEntity) {
        em.merge(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return em.createQuery("SELECT u FROM UserEntity u", UserEntity.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        em.remove(findById(id));
    }
}
