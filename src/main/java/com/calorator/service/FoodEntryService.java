package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodEntryService {

    public void save(FoodEntryDTO foodEntryDTO);

    public FoodEntryDTO findById(Long id);

    public List<FoodEntryDTO> findFoodEntriesLast7Days();

    public Long countFoodEntriesLast7Days();

    public void update(FoodEntryDTO foodEntryDTO);

    public List<FoodEntryDTO> findAll();

    public void delete(Long id);

    List<FoodEntryDTO> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
