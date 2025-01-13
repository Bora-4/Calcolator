package com.calorator.service;

import com.calorator.dto.CalorieThresholdDTO;
import java.util.Date;
import java.util.List;

public interface CalorieThresholdService {
    CalorieThresholdDTO getThresholdByUserIdAndDate(Long userId, Date date);
    CalorieThresholdDTO saveThreshold(CalorieThresholdDTO thresholdDTO);
    void updateTotalCalories(Long userId, int calories, Date date);
    boolean isThresholdExceeded(Long userId, Date date);
    List<Date> getExceededThresholdDates(Long userId, Date startDate, Date endDate);
}
