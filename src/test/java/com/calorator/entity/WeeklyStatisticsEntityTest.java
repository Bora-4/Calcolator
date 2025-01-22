package com.calorator.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyStatisticsEntityTest {

    private WeeklyStatisticsEntity weeklyStatisticsEntity;

    @BeforeEach
    void setUp() {
        weeklyStatisticsEntity = new WeeklyStatisticsEntity();
    }

    @Test
    void testId() {
        weeklyStatisticsEntity.setId(1L);
        assertEquals(1L, weeklyStatisticsEntity.getId());
    }

    @Test
    void testReport() {
        ReportEntity report = new ReportEntity();
        weeklyStatisticsEntity.setReport(report);
        assertEquals(report, weeklyStatisticsEntity.getReport());
    }

    @Test
    void testStatisticName() {
        String statisticName = "Calories Burned";
        weeklyStatisticsEntity.setStatisticName(statisticName);
        assertEquals(statisticName, weeklyStatisticsEntity.getStatisticName());
    }

    @Test
    void testStatisticValue() {
        int statisticValue = 1000;
        weeklyStatisticsEntity.setStatisticValue(statisticValue);
        assertEquals(statisticValue, weeklyStatisticsEntity.getStatisticValue());
    }
}
