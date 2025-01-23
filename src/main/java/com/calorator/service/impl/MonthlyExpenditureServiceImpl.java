package com.calorator.service.impl;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.entity.UserEntity;
import com.calorator.repository.FoodEntryRepository;
import com.calorator.repository.MonthlyExpenditureRepository;
import com.calorator.service.MonthlyExpenditureService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MonthlyExpenditureServiceImpl implements MonthlyExpenditureService {


    private final FoodEntryRepository foodEntryRepository;
    private final MonthlyExpenditureRepository monthlyExpenditureRepository;

    public MonthlyExpenditureServiceImpl(FoodEntryRepository foodEntryRepository, MonthlyExpenditureRepository monthlyExpenditureRepository){
        this.foodEntryRepository = foodEntryRepository;
        this.monthlyExpenditureRepository = monthlyExpenditureRepository;
    }
    private static final BigDecimal DEFAULT_LIMIT = BigDecimal.valueOf(1000);

    private final Map<Long, BigDecimal> userCustomLimits = new HashMap<>();

    @Override
    public void calculateMonthlySpending(Long userId, LocalDate month) {
        int monthInt = month.getMonthValue();
        int year = month.getYear();

        BigDecimal totalSpent = foodEntryRepository.calculateMonthlySpending(userId, monthInt, year);

        BigDecimal spendingLimit = userCustomLimits.getOrDefault(userId, DEFAULT_LIMIT);

        boolean isWarning = totalSpent.compareTo(spendingLimit) > 0;

        MonthlyExpenditureEntity expenditure = monthlyExpenditureRepository.findByUserIdAndMonth(userId, month);
        if (expenditure == null) {
            expenditure = new MonthlyExpenditureEntity();
            UserEntity user = new UserEntity();
            user.setId(userId);
            expenditure.setUser(user);
            expenditure.setMonth(month);
        }

        expenditure.setTotalSpent(totalSpent);
        expenditure.setWarning(isWarning);

        monthlyExpenditureRepository.save(expenditure);

        if (isWarning) {
            sendThresholdNotification(userId, totalSpent, spendingLimit);
        }
    }

    @Override
    public void setCustomLimit(Long userId, BigDecimal customLimit) {
        if (customLimit == null || customLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Custom limit must be a positive value.");
        }
        userCustomLimits.put(userId, customLimit);
    }


    private void sendThresholdNotification(Long userId, BigDecimal totalSpent, BigDecimal spendingLimit) {
        System.out.printf("User %d exceeded the spending limit of %s. Total spent: %s%n",
                userId, spendingLimit, totalSpent);
    }


    @Override
    public MonthlyExpenditureEntity getMonthlyExpenditure(Long userId, LocalDate month) {
        return monthlyExpenditureRepository.findByUserIdAndMonth(userId, month);
    }

}
