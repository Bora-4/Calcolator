package com.calorator.repository;

import com.calorator.entity.FoodEntryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodEntryRepository {
    void save(FoodEntryEntity foodEntry);
    FoodEntryEntity findById(int id);
    List<FoodEntryEntity> findFoodEntriesLast7Days();
    int countFoodEntriesLast7Days();
    void update(FoodEntryEntity foodEntry);
    List<FoodEntryEntity> findAll();
    void delete(int id);

    List<FoodEntryEntity> entryDateFiltering(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
