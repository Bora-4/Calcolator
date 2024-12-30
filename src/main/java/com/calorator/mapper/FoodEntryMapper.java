package com.calorator.mapper;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.entity.FoodEntryEntity;
import com.calorator.entity.UserEntity;

public class FoodEntryMapper {

    private FoodEntryMapper(){
        // private constructor
    }

    public static FoodEntryEntity toEntity(FoodEntryDTO foodEntryDTO, UserEntity user){
        if(foodEntryDTO == null || user == null){
            throw new NullPointerException("FoodEntryDTO or UserEntity must not be null.");
        }

        FoodEntryEntity foodEntry = new FoodEntryEntity();
        foodEntry.setId(foodEntryDTO.getId());
        foodEntry.setFoodName(foodEntryDTO.getFoodName());
        foodEntry.setUser(user);
        foodEntry.setCalories(foodEntryDTO.getCalories());
        foodEntry.setPrice(foodEntryDTO.getPrice());
        foodEntry.setEntryDate(foodEntryDTO.getEntryDate());
        foodEntry.setCreatedAt(foodEntryDTO.getCreatedAt());
        foodEntry.setUpdatedAt(foodEntryDTO.getUpdatedAt());

        return foodEntry;

    }

    public static FoodEntryDTO toDTO(FoodEntryEntity foodEntry){
        if (foodEntry == null){
            throw new NullPointerException("FoodEntryEntity must not be null.");
        }

        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(foodEntry.getId());
        foodEntryDTO.setFoodName(foodEntry.getFoodName());
        foodEntryDTO.setCalories(foodEntry.getCalories());
        foodEntryDTO.setPrice(foodEntry.getPrice());
        foodEntryDTO.setEntryDate(foodEntry.getEntryDate());
        foodEntryDTO.setCreatedAt(foodEntry.getCreatedAt());
        foodEntryDTO.setUpdatedAt(foodEntry.getUpdatedAt());

        if (foodEntry.getUser() != null) {
            foodEntryDTO.setUser(UserMapper.toDTO(foodEntry.getUser()));
        } else {
            foodEntryDTO.setUser(null);
        }

        return foodEntryDTO;

    }

}
