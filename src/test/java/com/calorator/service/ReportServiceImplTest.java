package com.calorator.service;

import com.calorator.dto.ReportDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.repository.ReportRepository;
import com.calorator.service.impl.ReportServiceImpl;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setReportDate(LocalDate.now());

        doNothing().when(reportRepository).save(any(ReportEntity.class));

        reportService.save(reportDTO);

        verify(reportRepository, times(1)).save(any(ReportEntity.class));
    }

    @Test
    void testFindById() {
        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setReportDate(LocalDate.now());

        when(reportRepository.findById(1L)).thenReturn(report);

        ReportDTO reportDTO = reportService.findById(1L);
        assertNotNull(reportDTO);
        assertEquals(1L, reportDTO.getId());
    }

    @Test
    void testFindAll() {
        ReportEntity report1 = new ReportEntity();
        report1.setId(1L);

        ReportEntity report2 = new ReportEntity();
        report2.setId(2L);

        when(reportRepository.findAll()).thenReturn(List.of(report1, report2));

        List<ReportDTO> reports = reportService.findAll();
        assertEquals(2, reports.size());
    }

    @Test
    void testUpdateReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);

        doNothing().when(reportRepository).update(any(ReportEntity.class));

        reportService.update(reportDTO);

        verify(reportRepository, times(1)).update(any(ReportEntity.class));
    }

    @Test
    void testDeleteReport() {
        doNothing().when(reportRepository).delete(1L);

        reportService.delete(1L);

        verify(reportRepository, times(1)).delete(1L);
    }

    @Test
    void testFindByReportDate() {
        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setReportDate(LocalDate.now());

        when(reportRepository.findByReportDate(LocalDate.now().toString())).thenReturn(report);

        ReportDTO reportDTO = reportService.findByReportDate(LocalDate.now().toString());
        assertNotNull(reportDTO);
        assertEquals(LocalDate.now(), reportDTO.getReportDate());
    }
}
