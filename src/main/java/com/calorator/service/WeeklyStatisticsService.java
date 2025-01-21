package com.calorator.service;

import com.calorator.dto.WeeklyStatisticsDTO;
import java.util.List;

public interface WeeklyStatisticsService {

    WeeklyStatisticsDTO createWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO, Long reportId);

    WeeklyStatisticsDTO updateWeeklyStatistics(Long id, WeeklyStatisticsDTO weeklyStatisticsDTO);

    WeeklyStatisticsDTO getWeeklyStatisticsById(Long id);

    List<WeeklyStatisticsDTO> getAllWeeklyStatistics();

    void deleteWeeklyStatistics(Long id);
}
