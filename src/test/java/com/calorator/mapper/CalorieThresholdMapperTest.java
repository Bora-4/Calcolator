package com.calorator.mapper;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CalorieThresholdMapperTest {

    @Test
    void testToEntity_validCalorieThresholdDTOProvided_validUserEntityProvided(){
        CalorieThresholdDTO calorieThresholdDTO = new CalorieThresholdDTO();
        calorieThresholdDTO.setThresholdId(1);
        calorieThresholdDTO.setThresholdDate(new Date());
        calorieThresholdDTO.setTotalCalories(2500);
        calorieThresholdDTO.setWarningTriggered(true);
        calorieThresholdDTO.setCalorieLimit(2000);
        calorieThresholdDTO.setCreatedAt(LocalDateTime.of(2025, 1, 22, 14, 50));
        calorieThresholdDTO.setUpdatedAt(LocalDateTime.of(2025, 1, 22, 14, 50));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");

        CalorieThresholdEntity calorieThreshold = CalorieThresholdMapper.toEntity(calorieThresholdDTO, user);

        assertNotNull(calorieThreshold);
        assertEquals(calorieThresholdDTO.getThresholdId(), calorieThreshold.getThresholdId());
        assertEquals(calorieThresholdDTO.getThresholdDate(), calorieThreshold.getThresholdDate());
        assertEquals(calorieThresholdDTO.getTotalCalories(), calorieThreshold.getTotalCalories());
        assertEquals(calorieThresholdDTO.isWarningTriggered(), calorieThreshold.isWarningTriggered());
        assertEquals(calorieThresholdDTO.getCalorieLimit(), calorieThreshold.getCalorieLimit());
        assertEquals(calorieThresholdDTO.getCreatedAt(), calorieThreshold.getCreatedAt());
        assertEquals(calorieThresholdDTO.getUpdatedAt(), calorieThreshold.getUpdatedAt());
        assertEquals(user, calorieThreshold.getUser());

    }

    @Test
    void testToEntity_validCalorieThresholdDTOProvided_NullUserEntityProvided(){
        CalorieThresholdDTO calorieThresholdDTO = new CalorieThresholdDTO();
        assertThrows(NullPointerException.class, () -> CalorieThresholdMapper.toEntity(calorieThresholdDTO, null));
    }

    @Test
    void testToEntity_nullCalorieThresholdDTOProvided_validUserEntityProvided(){
        UserEntity user = new UserEntity();
        assertThrows(NullPointerException.class, () -> CalorieThresholdMapper.toEntity(null, user));
    }

    @Test
    void testToDTO_validCalorieThresholdEntityProvided(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");
        user.setRole(UserEntity.Role.user);

        CalorieThresholdEntity calorieThreshold = new CalorieThresholdEntity();
        calorieThreshold.setThresholdId(1);
        calorieThreshold.setThresholdDate(new Date());
        calorieThreshold.setUser(user);
        calorieThreshold.setTotalCalories(2500);
        calorieThreshold.setWarningTriggered(true);
        calorieThreshold.setCalorieLimit(2000);
        calorieThreshold.setCreatedAt(LocalDateTime.of(2025, 1, 22, 14, 50));
        calorieThreshold.setUpdatedAt(LocalDateTime.of(2025, 1, 22, 14, 50));

        CalorieThresholdDTO calorieThresholdDTO = CalorieThresholdMapper.toDTO(calorieThreshold);
        assertNotNull(calorieThresholdDTO);
        assertEquals(calorieThresholdDTO.getThresholdId(), calorieThreshold.getThresholdId());
        assertEquals(calorieThresholdDTO.getThresholdDate(), calorieThreshold.getThresholdDate());
        assertEquals(calorieThresholdDTO.getTotalCalories(), calorieThreshold.getTotalCalories());
        assertEquals(calorieThresholdDTO.isWarningTriggered(), calorieThreshold.isWarningTriggered());
        assertEquals(calorieThresholdDTO.getCalorieLimit(), calorieThreshold.getCalorieLimit());
        assertEquals(calorieThresholdDTO.getCreatedAt(), calorieThreshold.getCreatedAt());
        assertEquals(calorieThresholdDTO.getUpdatedAt(), calorieThreshold.getUpdatedAt());
        assertNotNull(calorieThresholdDTO.getUser());
        assertEquals(user.getId(), calorieThresholdDTO.getUser().getId());



    }

    @Test
    void testToDTO_nullCalorieThresholdEntityProvided(){
        assertThrows(NullPointerException.class, () -> CalorieThresholdMapper.toDTO(null));
    }
}
