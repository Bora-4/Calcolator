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
