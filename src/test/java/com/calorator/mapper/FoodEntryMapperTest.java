package com.calorator.mapper;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.entity.FoodEntryEntity;
import com.calorator.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FoodEntryMapperTest {

    @Test
    void testToEntity_validFoodEntryDTOProvided_validUserEntityProvided(){

        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("pizza");
        foodEntryDTO.setCalories(250);
        foodEntryDTO.setPrice(15.25);
        foodEntryDTO.setEntryDate(LocalDateTime.of(2025, 1, 12, 20, 10));
        foodEntryDTO.setCreatedAt(LocalDateTime.of(2025, 1, 12, 20, 10));
        foodEntryDTO.setUpdatedAt(LocalDateTime.of(2025, 1, 12, 20, 10));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");

        FoodEntryEntity foodEntryEntity = FoodEntryMapper.toEntity(foodEntryDTO, user);

        assertNotNull(foodEntryEntity);
        assertEquals(foodEntryDTO.getId(), foodEntryEntity.getId());
        assertEquals(foodEntryDTO.getFoodName(), foodEntryEntity.getFoodName());
        assertEquals(user, foodEntryEntity.getUser());
        assertEquals(foodEntryDTO.getCalories(), foodEntryEntity.getCalories());
        assertEquals(foodEntryDTO.getPrice(), foodEntryEntity.getPrice());
        assertEquals(foodEntryDTO.getEntryDate(), foodEntryEntity.getEntryDate());
        assertEquals(foodEntryDTO.getCreatedAt(), foodEntryEntity.getCreatedAt());
        assertEquals(foodEntryDTO.getUpdatedAt(), foodEntryEntity.getUpdatedAt());

    }

    @Test
    void testToEntity_validFoodEntryDTOProvided_nullUserEntityProvided(){
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        assertThrows(NullPointerException.class, () -> FoodEntryMapper.toEntity(foodEntryDTO, null));
    }

    @Test
    void testToEntity_nullFoodEntryDTOProvided_validUserEntityProvided(){
        UserEntity user = new UserEntity();
        assertThrows(NullPointerException.class, ()-> FoodEntryMapper.toEntity(null, user));
    }

    @Test
    void testToDTO_validFoodEntryEntityProvided(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");
        user.setRole(UserEntity.Role.user);

        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        foodEntryEntity.setId(1L);
        foodEntryEntity.setFoodName("pizza");
        foodEntryEntity.setUser(user);
        foodEntryEntity.setCalories(250);
        foodEntryEntity.setPrice(15.25);
        foodEntryEntity.setEntryDate(LocalDateTime.of(2025, 1, 12, 20, 10));
        foodEntryEntity.setCreatedAt(LocalDateTime.of(2025, 1, 12, 20, 10));
        foodEntryEntity.setUpdatedAt(LocalDateTime.of(2025, 1, 12, 20, 10));

        FoodEntryDTO foodEntryDTO = FoodEntryMapper.toDTO(foodEntryEntity);

        assertNotNull(foodEntryDTO);
        assertEquals(foodEntryEntity.getId(), foodEntryDTO.getId());
        assertEquals(foodEntryEntity.getFoodName(), foodEntryDTO.getFoodName());
        assertEquals(foodEntryEntity.getCalories(), foodEntryDTO.getCalories());
        assertEquals(foodEntryEntity.getPrice(), foodEntryDTO.getPrice());
        assertEquals(foodEntryEntity.getEntryDate(), foodEntryDTO.getEntryDate());
        assertEquals(foodEntryEntity.getCreatedAt(), foodEntryDTO.getCreatedAt());
        assertEquals(foodEntryEntity.getUpdatedAt(), foodEntryDTO.getUpdatedAt());
        assertNotNull(foodEntryDTO.getUser());
        assertEquals(user.getId(), foodEntryDTO.getUser().getId());
    }

    @Test
    void testToDTO_nullFoodEntryEntityProvided(){
        assertThrows(NullPointerException.class, ()-> FoodEntryMapper.toDTO(null));
    }


}
