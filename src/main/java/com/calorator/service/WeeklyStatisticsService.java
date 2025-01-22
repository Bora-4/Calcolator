package com.calorator.service;

import com.calorator.dto.WeeklyStatisticsDTO;
import java.util.List;

public interface WeeklyStatisticsService {

    void saveWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO);
    WeeklyStatisticsDTO findWeeklyStatisticsById(Long id);
    List<WeeklyStatisticsDTO> findAllWeeklyStatistics();
    void updateWeeklyStatistics( WeeklyStatisticsDTO weeklyStatisticsDTO);
    void deleteWeeklyStatistics(Long id);

}