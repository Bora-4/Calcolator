package com.calorator.repository.impl;

import com.calorator.entity.UserEntity;
import com.calorator.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public UserEntity findById(int id) {
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
    public void delete(int id) {
        em.remove(findById(id));
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user by email", e);
        }
    }

    @Override
    public Optional<UserEntity> findByUserName(String name) {
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.name = :name", UserEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user by username", e);
        }
    }


    @Override
    public Optional<UserEntity> findByPassword(String password) {
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.password = :password", UserEntity.class)
                    .setParameter("password", password)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user by username", e);
        }
    }
}
