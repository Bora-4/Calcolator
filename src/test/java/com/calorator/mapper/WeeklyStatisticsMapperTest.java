package com.calorator.mapper;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.WeeklyStatisticsEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeeklyStatisticsMapperTest {

    @Test
    void testToEntity_validWeeklyStatisticsDTOProvided_validReportEntityProvided() {
        WeeklyStatisticsDTO weeklyStatisticsDTO = new WeeklyStatisticsDTO();
        weeklyStatisticsDTO.setId(1L);
        weeklyStatisticsDTO.setStatisticName("Calories Burned");
        weeklyStatisticsDTO.setStatisticValue(3500);

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);
        reportEntity.setReportDate(java.time.LocalDate.of(2025, 1, 15));
        reportEntity.setCreatedAt(java.time.LocalDateTime.of(2025, 1, 15, 10, 0));
        reportEntity.setUpdatedAt(java.time.LocalDateTime.of(2025, 1, 15, 10, 0));

        WeeklyStatisticsEntity weeklyStatisticsEntity = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, reportEntity);

        assertNotNull(weeklyStatisticsEntity);
        assertEquals(weeklyStatisticsDTO.getId(), weeklyStatisticsEntity.getId());
        assertEquals(weeklyStatisticsDTO.getStatisticName(), weeklyStatisticsEntity.getStatisticName());
        assertEquals(weeklyStatisticsDTO.getStatisticValue(), weeklyStatisticsEntity.getStatisticValue());
        assertEquals(reportEntity, weeklyStatisticsEntity.getReport());
    }

    @Test
    void testToEntity_validWeeklyStatisticsDTOProvided_nullReportEntityProvided() {
        WeeklyStatisticsDTO weeklyStatisticsDTO = new WeeklyStatisticsDTO();
        weeklyStatisticsDTO.setId(1L);
        weeklyStatisticsDTO.setStatisticName("Calories Burned");
        weeklyStatisticsDTO.setStatisticValue(3500);

        assertThrows(NullPointerException.class, () -> WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, null));
    }

    @Test
    void testToEntity_nullWeeklyStatisticsDTOProvided_validReportEntityProvided() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);

        assertThrows(NullPointerException.class, () -> WeeklyStatisticsMapper.toEntity(null, reportEntity));
    }

    @Test
    void testToDTO_validWeeklyStatisticsEntityProvided() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);
        reportEntity.setReportDate(java.time.LocalDate.of(2025, 1, 15));
        reportEntity.setCreatedAt(java.time.LocalDateTime.of(2025, 1, 15, 10, 0));
        reportEntity.setUpdatedAt(java.time.LocalDateTime.of(2025, 1, 15, 10, 0));

        WeeklyStatisticsEntity weeklyStatisticsEntity = new WeeklyStatisticsEntity();
        weeklyStatisticsEntity.setId(1L);
        weeklyStatisticsEntity.setStatisticName("Calories Burned");
        weeklyStatisticsEntity.setStatisticValue(3500);
        weeklyStatisticsEntity.setReport(reportEntity);

        WeeklyStatisticsDTO weeklyStatisticsDTO = WeeklyStatisticsMapper.toDTO(weeklyStatisticsEntity);

        assertNotNull(weeklyStatisticsDTO);
        assertEquals(weeklyStatisticsEntity.getId(), weeklyStatisticsDTO.getId());
        assertEquals(weeklyStatisticsEntity.getStatisticName(), weeklyStatisticsDTO.getStatisticName());
        assertEquals(weeklyStatisticsEntity.getStatisticValue(), weeklyStatisticsDTO.getStatisticValue());
        assertNotNull(weeklyStatisticsDTO.getReportDTO());
        assertEquals(reportEntity.getId(), weeklyStatisticsDTO.getReportDTO().getId());
    }

    @Test
    void testToDTO_nullWeeklyStatisticsEntityProvided() {
        assertThrows(NullPointerException.class, () -> WeeklyStatisticsMapper.toDTO(null));
    }

    @Test
    void testToDTO_weeklyStatisticsEntityWithNullReportProvided() {
        WeeklyStatisticsEntity weeklyStatisticsEntity = new WeeklyStatisticsEntity();
        weeklyStatisticsEntity.setId(1L);
        weeklyStatisticsEntity.setStatisticName("Calories Burned");
        weeklyStatisticsEntity.setStatisticValue(3500);
        weeklyStatisticsEntity.setReport(null);

        WeeklyStatisticsDTO weeklyStatisticsDTO = WeeklyStatisticsMapper.toDTO(weeklyStatisticsEntity);

        assertNotNull(weeklyStatisticsDTO);
        assertEquals(weeklyStatisticsEntity.getId(), weeklyStatisticsDTO.getId());
        assertEquals(weeklyStatisticsEntity.getStatisticName(), weeklyStatisticsDTO.getStatisticName());
        assertEquals(weeklyStatisticsEntity.getStatisticValue(), weeklyStatisticsDTO.getStatisticValue());
        assertNull(weeklyStatisticsDTO.getReportDTO());
    }
}
