package com.calorator.mapper;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CalorieThresholdMapper {

    public CalorieThresholdEntity toEntity(CalorieThresholdDTO dto, UserEntity user) {
        CalorieThresholdEntity entity = new CalorieThresholdEntity();
        entity.setThresholdId(dto.getThresholdId());
        entity.setUser(user);
        entity.setThresholdDate(dto.getThresholdDate());
        entity.setTotalCalories(dto.getTotalCalories());
        entity.setWarningTriggered(dto.isWarningTriggered());
        entity.setCalorieLimit(dto.getCalorieLimit());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public CalorieThresholdDTO toDTO(CalorieThresholdEntity entity) {
        CalorieThresholdDTO dto = new CalorieThresholdDTO();
        dto.setThresholdId(entity.getThresholdId());
        dto.setThresholdDate(entity.getThresholdDate());
        dto.setTotalCalories(entity.getTotalCalories());
        dto.setWarningTriggered(entity.isWarningTriggered());
        dto.setCalorieLimit(entity.getCalorieLimit());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getUser() != null) {
            dto.setUser(UserMapper.toDTO(entity.getUser()));
        } else {
            dto.setUser(null);
        }
        return dto;
    }


}
