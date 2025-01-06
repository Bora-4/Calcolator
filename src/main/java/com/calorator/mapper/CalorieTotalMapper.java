package com.calorator.mapper;

import com.calorator.dto.CalorieTotalDTO;
import com.calorator.entity.CalorieTotalEntity;
import org.springframework.stereotype.Component;

@Component
public class CalorieTotalMapper {
    public CalorieTotalDTO toDTO(CalorieTotalEntity entity) {
        CalorieTotalDTO dto = new CalorieTotalDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setDate(entity.getDate());
        dto.setTotalCalories(entity.getTotalCalories());
        dto.setIsWarningTriggered(entity.isWarningTriggered());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public CalorieTotalEntity toEntity(CalorieTotalDTO dto) {
        CalorieTotalEntity entity = new CalorieTotalEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setDate(dto.getDate());
        entity.setTotalCalories(dto.getTotalCalories());
        entity.setWarningTriggered(dto.getIsWarningTriggered());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}

