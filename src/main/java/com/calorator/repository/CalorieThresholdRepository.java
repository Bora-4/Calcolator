package com.calorator.repository;

import com.calorator.entity.CalorieThresholdEntity;
import java.util.Date;
import java.util.List;

public interface CalorieThresholdRepository {

    CalorieThresholdEntity findByUserIdAndDate(Long userId, Date date);
    CalorieThresholdEntity save(CalorieThresholdEntity calorieThresholdEntity);
    void deleteById(Long id);
    List<Date> findExceededThresholdDates(Long userId, Date startDate, Date endDate);
}
