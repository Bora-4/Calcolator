package com.calorator.repository;

import com.calorator.entity.FoodEntryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodEntryRepository {
    void save(FoodEntryEntity foodEntry);
    FoodEntryEntity findById(Long id);
    List<FoodEntryEntity> findFoodEntriesLast7Days();
    Long countFoodEntriesLast7Days();
    void update(FoodEntryEntity foodEntry);
    List<FoodEntryEntity> findAll();
    void delete(Long id);
}
