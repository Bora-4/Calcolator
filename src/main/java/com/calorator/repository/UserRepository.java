package com.calorator.repository;

import com.calorator.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    void save(UserEntity userEntity);
    UserEntity findById(Long id);
    UserEntity findByName(String name);
    void update(UserEntity userEntity);
    List<UserEntity> findAll();
    void delete(Long id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserName(String username);
}
