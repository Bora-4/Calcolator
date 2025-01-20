package com.calorator.mapper;

import com.calorator.dto.UserSpendingDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.entity.UserSpendingEntity;

public class UserSpendingMapper {

    private UserSpendingMapper(){
        // private constructor
    }

    public static UserSpendingEntity toEntity(UserSpendingDTO userSpendingDTO, UserEntity user, ReportEntity report){
        if (userSpendingDTO == null || user == null || report == null){
            throw new NullPointerException("Neither UserSpendingDTO nor user nor report can be null!");
        }

        UserSpendingEntity userSpending = new UserSpendingEntity();
        userSpending.setId(userSpendingDTO.getId());
        userSpending.setUser(user);
        userSpending.setReport(report);
        userSpending.setTotalSpent(userSpendingDTO.getTotalSpent());

        return userSpending;
    }

    public static UserSpendingDTO toDTO(UserSpendingEntity userSpending){

        if(userSpending == null){
            throw new NullPointerException("UserSpendingEntity cannot be null!");
        }

        UserSpendingDTO userSpendingDTO = new UserSpendingDTO();
        userSpendingDTO.setId(userSpending.getId());
        userSpendingDTO.setTotalSpent(userSpending.getTotalSpent());

        if(userSpending.getUser() != null){
            userSpendingDTO.setUser(UserMapper.toDTO(userSpending.getUser()));
        } else {
            userSpendingDTO.setUser(null);
        }

        if (userSpending.getReport() != null){
            userSpendingDTO.setReport(ReportMapper.toDTO(userSpending.getReport()));;
        } else {
            userSpendingDTO.setReport(null);
        }

        return userSpendingDTO;
    }
}
