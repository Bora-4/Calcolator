package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.entity.FoodEntryEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.FoodEntryMapper;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface FoodEntryService {

    public void save(FoodEntryDTO foodEntryDTO);

    public FoodEntryDTO findById(Long id);

    public List<FoodEntryDTO> findFoodEntriesLast7Days();

    public Long countFoodEntriesLast7Days();

    public void update(FoodEntryDTO foodEntryDTO);

    public List<FoodEntryDTO> findAll();

    public void delete(Long id);
}
