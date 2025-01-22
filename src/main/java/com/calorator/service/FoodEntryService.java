package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FoodEntryService {

    public void save(FoodEntryDTO foodEntryDTO);

    public FoodEntryDTO findById(Long id);

    public List<FoodEntryDTO> findFoodEntriesLast7Days(long userId);

    public Long countFoodEntriesLast7Days();

    public void update(FoodEntryDTO foodEntryDTO);

    public List<FoodEntryDTO> findAll();

    public void delete(Long id);

    public BigDecimal calculateMonthlySpending(Long userId, int month, int year);

    public void validateFoodEntry(FoodEntryDTO foodEntryDTO);

    List<FoodEntryDTO> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
