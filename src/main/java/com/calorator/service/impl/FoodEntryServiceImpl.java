package com.calorator.service.impl;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.entity.FoodEntryEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.FoodEntryMapper;
import com.calorator.repository.FoodEntryRepository;
import com.calorator.repository.UserRepository;
import com.calorator.service.FoodEntryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class FoodEntryServiceImpl implements FoodEntryService {

    private final FoodEntryRepository foodEntryRepository;
    private final UserRepository userRepository;

    public FoodEntryServiceImpl(FoodEntryRepository foodEntryRepository, UserRepository userRepository){
        this.foodEntryRepository = foodEntryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(FoodEntryDTO foodEntryDTO) {

        if( foodEntryDTO.getUser() == null || foodEntryDTO.getUser().getId() == null){
            throw new IllegalArgumentException("User information is required.");
        }

        UserEntity user = userRepository.findById(foodEntryDTO.getUser().getId());

        if (user == null) {
            throw new EntityNotFoundException("User with id " + foodEntryDTO.getUser().getId() + " was not found.");
        }

        FoodEntryEntity foodEntry = FoodEntryMapper.toEntity(foodEntryDTO, user);
        foodEntryRepository.save(foodEntry);

    }

    @Override
    public FoodEntryDTO findById(Long id) {
        FoodEntryEntity foodEntry = foodEntryRepository.findById(id);
        if(foodEntry != null){
            return FoodEntryMapper.toDTO(foodEntry);
        }
        throw new EntityNotFoundException("Food entry with id " + id + " was not found.");
    }

    @Override
    public List<FoodEntryDTO> findFoodEntriesLast7Days() {
        List<FoodEntryEntity> foodEntries = foodEntryRepository.findFoodEntriesLast7Days();
        return foodEntries.stream()
                .map(FoodEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countFoodEntriesLast7Days() {
        return foodEntryRepository.countFoodEntriesLast7Days();
    }

    @Override
    public void update(FoodEntryDTO foodEntryDTO) {
        if (foodEntryDTO.getId() == null){
            throw new IllegalArgumentException("Food entry id is required for updating.");
        }

        FoodEntryEntity existingFoodEntry = foodEntryRepository.findById(foodEntryDTO.getId());

        if (existingFoodEntry == null) {
            throw new EntityNotFoundException("Food entry with id " + foodEntryDTO.getId() + " was not found.");
        }

        UserEntity user = userRepository.findById(foodEntryDTO.getUser().getId());
        if (user == null) {
            throw new EntityNotFoundException("User with id " + foodEntryDTO.getUser().getId() + " was not found.");
        }

        FoodEntryEntity updatedFoodEntry = FoodEntryMapper.toEntity(foodEntryDTO, user);
        updatedFoodEntry.setCreatedAt(existingFoodEntry.getCreatedAt());
        foodEntryRepository.update(updatedFoodEntry);

    }

    @Override
    public List<FoodEntryDTO> findAll() {
        List<FoodEntryEntity> foodEntries = foodEntryRepository.findAll();
        return foodEntries.stream()
                .map(FoodEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        FoodEntryEntity foodEntry = foodEntryRepository.findById(id);
        if (foodEntry == null) {
            throw new EntityNotFoundException("Food entry with id " + id + " was not found.");
        }
        foodEntryRepository.delete(id);
    }

    @Override
    public List<FoodEntryDTO> entryDateFiltering(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        if (userId == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("userId, startDate and endDate must not be null.");
        }

        List<FoodEntryEntity> foodEntries = foodEntryRepository.entryDateFiltering(userId, startDate, endDate);
        return foodEntries.stream()
                .map(FoodEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void validateFoodEntry(FoodEntryDTO foodEntryDTO) {
        if (foodEntryDTO == null) {
            throw new IllegalArgumentException("FoodEntryDTO must not be null.");
        }

        String foodName = foodEntryDTO.getFoodName();
        int calories = foodEntryDTO.getCalories();
        LocalDateTime entryDate = foodEntryDTO.getEntryDate();
        LocalDateTime now = LocalDateTime.now();

        if (foodName == null || foodName.isEmpty()) {
            throw new IllegalArgumentException("Food name must not be empty.");
        }

        if (calories <= 0) {
            throw new IllegalArgumentException("Calories must be greater than 0.");
        }

        if (entryDate == null || entryDate.isAfter(now)) {
            throw new IllegalArgumentException("Entry date must be in the past.");
        }

    }


}