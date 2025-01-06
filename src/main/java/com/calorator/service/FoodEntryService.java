package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface FoodEntryService {

    public void save(FoodEntryDTO foodEntryDTO);

    public FoodEntryDTO findById(int id);

    public List<FoodEntryDTO> findFoodEntriesLast7Days();

    public int countFoodEntriesLast7Days();

    public void update(FoodEntryDTO foodEntryDTO);

    public List<FoodEntryDTO> findAll();

    public void delete(int id);

    List<FoodEntryDTO> entryDateFiltering(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
