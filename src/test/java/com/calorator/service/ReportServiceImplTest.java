package com.calorator.service;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.repository.ReportRepository;
import com.calorator.service.impl.ReportServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private WeeklyStatisticsService weeklyStatisticsService;

    @Mock
    private FoodEntryService foodEntryService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);
        reportEntity.setReportDate(LocalDate.now());

        when(reportRepository.findById(1L)).thenReturn(reportEntity);

        ReportDTO result = reportService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_NotFound() {
        when(reportRepository.findById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> reportService.findById(1L));
    }

    @Test
    void testFindAll_Success() {
        ReportEntity report1 = new ReportEntity();
        report1.setId(1L);

        ReportEntity report2 = new ReportEntity();
        report2.setId(2L);

        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        List<ReportDTO> result = reportService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testDelete_Success() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);

        when(reportRepository.findById(1L)).thenReturn(reportEntity);

        reportService.delete(1L);

        verify(reportRepository, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(reportRepository.findById(1L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> reportService.delete(1L));
    }

    @Test
    void testFindByReportDate_Success() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(1L);
        reportEntity.setReportDate(LocalDate.now());

        when(reportRepository.findByReportDate(LocalDate.now().toString())).thenReturn(reportEntity);

        ReportDTO result = reportService.findByReportDate(LocalDate.now().toString());

        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getReportDate());
    }

    @Test
    void testFindByReportDate_NotFound() {
        when(reportRepository.findByReportDate("2025-01-01")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> reportService.findByReportDate("2025-01-01"));
    }

    @Test
    void testFindByReportDate_NullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> reportService.findByReportDate(null));
        assertThrows(IllegalArgumentException.class, () -> reportService.findByReportDate(""));
    }

    @Test
    void testGetWeeklyStatistics_Success() {
        WeeklyStatisticsDTO stat1 = new WeeklyStatisticsDTO();
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setId(1L);
        stat1.setReportDTO(reportDTO1);

        WeeklyStatisticsDTO stat2 = new WeeklyStatisticsDTO();
        ReportDTO reportDTO2 = new ReportDTO();
        reportDTO2.setId(2L);
        stat2.setReportDTO(reportDTO2);

        when(weeklyStatisticsService.findAllWeeklyStatistics()).thenReturn(List.of(stat1, stat2));

        List<WeeklyStatisticsDTO> result = reportService.getWeeklyStatistics(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetWeeklyStatistics_NoMatch() {
        WeeklyStatisticsDTO stat = new WeeklyStatisticsDTO();
        ReportDTO reportDTO = new  ReportDTO();
        reportDTO.setId(2L);
        stat.setReportDTO(reportDTO);

        when(weeklyStatisticsService.findAllWeeklyStatistics()).thenReturn(List.of(stat));

        List<WeeklyStatisticsDTO> result = reportService.getWeeklyStatistics(1L);

        assertTrue(result.isEmpty());
    }
}
