package com.calorator.repository;

import com.calorator.entity.UserSpendingEntity;
import java.util.List;

public interface UserSpendingRepository {

    void save(UserSpendingEntity userSpendingEntity);

    void update(UserSpendingEntity userSpendingEntity);

    void delete(Long spendingId);

    UserSpendingEntity findById(Long spendingId);

    List<UserSpendingEntity> findAll();
}
