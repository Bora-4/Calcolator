package com.calorator.mapper;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.WeeklyStatisticsEntity;

public class WeeklyStatisticsMapper {

    private WeeklyStatisticsMapper(){
        // private constructor
    }

    public static WeeklyStatisticsEntity toEntity(WeeklyStatisticsDTO weeklyStatisticsDTO, ReportEntity report){
        if (weeklyStatisticsDTO == null || report == null){
            throw new NullPointerException("WeeklyStatisticsDTO or ReportEntity cannot be null.");
        }

        WeeklyStatisticsEntity weeklyStatistics = new WeeklyStatisticsEntity();
        weeklyStatistics.setId(weeklyStatisticsDTO.getId());
        weeklyStatistics.setReport(report);
        weeklyStatistics.setStatisticName(weeklyStatisticsDTO.getStatisticName());
        weeklyStatistics.setStatisticValue(weeklyStatisticsDTO.getStatisticValue());

        return weeklyStatistics;
    }

    public static WeeklyStatisticsDTO toDTO(WeeklyStatisticsEntity weeklyStatistics){
        if (weeklyStatistics == null){
            throw new NullPointerException("WeeklyStatisticsEntity cannot be null.");
        }

        WeeklyStatisticsDTO weeklyStatisticsDTO = new WeeklyStatisticsDTO();
        weeklyStatisticsDTO.setId(weeklyStatistics.getId());
        weeklyStatisticsDTO.setStatisticName(weeklyStatistics.getStatisticName());
        weeklyStatisticsDTO.setStatisticValue(weeklyStatistics.getStatisticValue());

        if(weeklyStatistics.getReport() != null){
            weeklyStatisticsDTO.setReportDTO(ReportMapper.toDTO(weeklyStatistics.getReport()));
        } else {
            weeklyStatisticsDTO.setReportDTO(null);
        }

        return weeklyStatisticsDTO;
    }
}
