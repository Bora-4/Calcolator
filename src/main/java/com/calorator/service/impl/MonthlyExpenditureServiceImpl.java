package com.calorator.service.impl;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.entity.UserEntity;
import com.calorator.repository.FoodEntryRepository;
import com.calorator.repository.MonthlyExpenditureRepository;
import com.calorator.repository.UserRepository;
import com.calorator.service.MonthlyExpenditureService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MonthlyExpenditureServiceImpl implements MonthlyExpenditureService {


    private final FoodEntryRepository foodEntryRepository;
    private final MonthlyExpenditureRepository monthlyExpenditureRepository;
    private final UserRepository userRepository;

    public MonthlyExpenditureServiceImpl(FoodEntryRepository foodEntryRepository, MonthlyExpenditureRepository monthlyExpenditureRepository, UserRepository userRepository){
        this.foodEntryRepository = foodEntryRepository;
        this.monthlyExpenditureRepository = monthlyExpenditureRepository;
        this.userRepository = userRepository;
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


    public void sendThresholdNotification(Long userId, BigDecimal totalSpent, BigDecimal spendingLimit) {
        System.out.printf("User %d exceeded the spending limit of %s. Total spent: %s%n",
                userId, spendingLimit, totalSpent);
    }


    @Override
    public BigDecimal getMonthlyExpenditure(Long userId, LocalDate month) {
        System.out.println("Calculating expenditure for UserId: " + userId + ", Month: " + month.getMonthValue() + ", Year: " + month.getYear());
        return monthlyExpenditureRepository.calculateMonthlySpending(userId, month.getMonthValue(), month.getYear());
    }

    @Override
    public Map<Long, BigDecimal> findUsersExceedingLimitForPreviousMonth() {
        LocalDate now = LocalDate.now();
        LocalDate previousMonth = now.minusMonths(1);
        int monthInt = previousMonth.getMonthValue();
        int year = previousMonth.getYear();

        Map<Long, BigDecimal> usersExceedingLimit = new HashMap<>();

        List<Long> userIds = (userRepository.findAll()).stream().map(UserEntity::getId).toList();

        for (Long userId : userIds) {
            BigDecimal totalSpent = foodEntryRepository.calculateMonthlySpending(userId, monthInt, year);
            BigDecimal spendingLimit = BigDecimal.valueOf(1000);

            if (totalSpent.compareTo(spendingLimit) > 0) {
                usersExceedingLimit.put(userId, totalSpent);

                sendThresholdNotification(userId, totalSpent, spendingLimit);
            }
        }

        return usersExceedingLimit;
    }



}
