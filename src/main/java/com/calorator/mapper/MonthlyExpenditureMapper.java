package com.calorator.mapper;

import com.calorator.dto.MonthlyExpenditureDTO;
import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.entity.UserEntity;

public class MonthlyExpenditureMapper {

    private MonthlyExpenditureMapper(){
        //private constructor
    }

    public static MonthlyExpenditureEntity toEntity(MonthlyExpenditureDTO expenditureDTO, UserEntity user){
        if(expenditureDTO == null || user == null) {
            throw new NullPointerException("MonthlyExpemditureDTO or UserEntity cannot be null.");
        }

        MonthlyExpenditureEntity expenditure = new MonthlyExpenditureEntity();
        expenditure.setId(expenditureDTO.getId());
        expenditure.setUser(user);
        expenditure.setMonth(expenditureDTO.getMonth());
        expenditure.setTotalSpent(expenditureDTO.getTotalSpent());
        expenditure.setWarning(expenditureDTO.isWarning());
        expenditure.setCreatedAt(expenditureDTO.getCreatedAt());
        expenditure.setUpdatedAt(expenditureDTO.getUpdatedAt());

        return expenditure;
    }

    public static MonthlyExpenditureDTO toDTO(MonthlyExpenditureEntity expenditure){
        if( expenditure == null){
            throw new NullPointerException("MonthlyExpenditureEntity cannot be null.");
        }

        MonthlyExpenditureDTO expenditureDTO = new MonthlyExpenditureDTO();

        expenditureDTO.setId(expenditure.getId());
        expenditureDTO.setMonth(expenditure.getMonth());
        expenditureDTO.setTotalSpent(expenditure.getTotalSpent());
        expenditureDTO.setWarning(expenditure.isWarning());
        expenditureDTO.setCreatedAt(expenditure.getCreatedAt());
        expenditureDTO.setUpdatedAt(expenditure.getUpdatedAt());

        if(expenditureDTO.getUser() != null) {
            expenditureDTO.setUser(UserMapper.toDTO(expenditure.getUser()));
        } else {
            expenditureDTO.setUser(null);
        }

        return expenditureDTO;
    }
}
