package com.calorator.mapper;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.entity.UserEntity;


public class CalorieThresholdMapper {

    private CalorieThresholdMapper(){
        //private constructor
    }

    public static CalorieThresholdEntity toEntity(CalorieThresholdDTO thresholdDTO, UserEntity user) {
        if(thresholdDTO == null || user == null){
            throw new NullPointerException("CalorieThresholdDTO or UserEntity must not be empty.");
        }
        CalorieThresholdEntity thresholdEntity = new CalorieThresholdEntity();
        thresholdEntity.setThresholdId(thresholdDTO.getThresholdId());
        thresholdEntity.setUser(user);
        thresholdEntity.setThresholdDate(thresholdDTO.getThresholdDate());
        thresholdEntity.setTotalCalories(thresholdDTO.getTotalCalories());
        thresholdEntity.setWarningTriggered(thresholdDTO.isWarningTriggered());
        thresholdEntity.setCreatedAt(thresholdDTO.getCreatedAt());
        thresholdEntity.setUpdatedAt(thresholdDTO.getUpdatedAt());
        return thresholdEntity;
    }

    public static CalorieThresholdDTO toDTO(CalorieThresholdEntity entity) {
        if( entity == null){
            throw new NullPointerException("CalorieThresholdEntity must not be nulll.");
        }
        CalorieThresholdDTO dto = new CalorieThresholdDTO();
        dto.setThresholdId(entity.getThresholdId());
        dto.setThresholdDate(entity.getThresholdDate());
        dto.setTotalCalories(entity.getTotalCalories());
        dto.setWarningTriggered(entity.isWarningTriggered());
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
