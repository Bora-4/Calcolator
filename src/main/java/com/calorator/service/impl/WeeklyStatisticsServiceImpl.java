package com.calorator.service.impl;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.mapper.WeeklyStatisticsMapper;
import com.calorator.repository.ReportRepository;
import com.calorator.repository.WeeklyStatisticsRepository;
import com.calorator.service.WeeklyStatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyStatisticsServiceImpl implements WeeklyStatisticsService {

    private final WeeklyStatisticsRepository weeklyStatisticsRepository;
    private final ReportRepository reportRepository;

    public WeeklyStatisticsServiceImpl(WeeklyStatisticsRepository weeklyStatisticsRepository, ReportRepository reportRepository) {
        this.weeklyStatisticsRepository = weeklyStatisticsRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public WeeklyStatisticsDTO createWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO, Long reportId) {
        ReportEntity report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with ID: " + reportId));

        WeeklyStatisticsEntity entity = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, report);
        entity = weeklyStatisticsRepository.save(entity);
        return WeeklyStatisticsMapper.toDTO(entity);
    }

    @Override
    public WeeklyStatisticsDTO updateWeeklyStatistics(Long id, WeeklyStatisticsDTO weeklyStatisticsDTO) {
        WeeklyStatisticsEntity existingEntity = weeklyStatisticsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Weekly statistics not found with ID: " + id));

        existingEntity.setStatisticName(weeklyStatisticsDTO.getStatisticName());
        existingEntity.setStatisticValue(weeklyStatisticsDTO.getStatisticValue());
        WeeklyStatisticsEntity updatedEntity = weeklyStatisticsRepository.save(existingEntity);

        return WeeklyStatisticsMapper.toDTO(updatedEntity);
    }

    @Override
    public WeeklyStatisticsDTO getWeeklyStatisticsById(Long id) {
        WeeklyStatisticsEntity entity = weeklyStatisticsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Weekly statistics not found with ID: " + id));

        return WeeklyStatisticsMapper.toDTO(entity);
    }

    @Override
    public List<WeeklyStatisticsDTO> getAllWeeklyStatistics() {
        return weeklyStatisticsRepository.findAll()
                .stream()
                .map(WeeklyStatisticsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWeeklyStatistics(Long id) {
        if (!weeklyStatisticsRepository.existsById(id)) {
            throw new IllegalArgumentException("Weekly statistics not found with ID: " + id);
        }
        weeklyStatisticsRepository.deleteById(id);
    }
}
