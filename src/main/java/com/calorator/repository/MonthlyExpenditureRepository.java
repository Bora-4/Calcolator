package com.calorator.repository;

import com.calorator.entity.MonthlyExpenditureEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface MonthlyExpenditureRepository {

    void save(MonthlyExpenditureEntity monthlyExpenditure);

    BigDecimal calculateMonthlySpending(Long userId, int month, int year);

    MonthlyExpenditureEntity findByUserIdAndMonth(Long userId, LocalDate month);


}
