package com.calorator.service;

import com.calorator.entity.MonthlyExpenditureEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MonthlyExpenditureService {
    void calculateMonthlySpending(Long userId, LocalDate month);

    void setCustomLimit(Long userId, BigDecimal customLimit);

    MonthlyExpenditureEntity getMonthlyExpenditure(Long userId, LocalDate month);

}
