package com.calorator.service;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.WeeklyStatisticsDTO;

import java.util.List;

public interface ReportService {

    void save(ReportDTO reportDTO);

    ReportDTO findById(Long id);

    List<ReportDTO> findAll();

    void update(ReportDTO reportDTO);

    void delete(Long id);

    ReportDTO findByReportDate(String reportDate);

    List<WeeklyStatisticsDTO> getWeeklyStatistics(Long reportId);
}
