package com.calorator.repository;

import com.calorator.entity.FoodEntryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodEntryRepository {
    void save(FoodEntryEntity foodEntry);
    FoodEntryEntity findById(Long id);
    List<FoodEntryEntity> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<FoodEntryEntity> findFoodEntriesLast7Days();
    Long countFoodEntriesLast7Days();
    void update(FoodEntryEntity foodEntry);
    List<FoodEntryEntity> findAll();
    void delete(Long id);
}
