package com.calorator.repository;

import com.calorator.entity.MonthlyExpenditureEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MonthlyExpenditureRepository {

    void save(MonthlyExpenditureEntity monthlyExpenditure);
    MonthlyExpenditureEntity findByUserIdAndMonth(Long userId, LocalDate month);


}
