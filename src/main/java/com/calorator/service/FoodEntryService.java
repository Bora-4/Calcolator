package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodEntryService {
    void save(FoodEntryDTO foodEntryDTO);
    FoodEntryDTO findById(Long id);
    List<FoodEntryDTO> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<FoodEntryDTO> findFoodEntriesLast7Days();
    Long countFoodEntriesLast7Days();
    void update(FoodEntryDTO foodEntryDTO);
    List<FoodEntryDTO> findAll();
    void delete(Long id);
}
