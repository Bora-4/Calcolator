package com.calorator.service.impl;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.dto.ReportDTO;
import com.calorator.dto.UserDTO;
import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.WeeklyStatisticsMapper;
import com.calorator.mapper.ReportMapper;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.WeeklyStatisticsRepository;
import com.calorator.service.WeeklyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyStatisticsServiceImpl implements WeeklyStatisticsService {

    private final WeeklyStatisticsRepository weeklyStatisticsRepository;

    @Autowired
    public WeeklyStatisticsServiceImpl(WeeklyStatisticsRepository weeklyStatisticsRepository) {
        this.weeklyStatisticsRepository = weeklyStatisticsRepository;
    }

    @Override
    public WeeklyStatisticsDTO createWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO, Long reportId) {
        return null;
    }

    @Override
    public WeeklyStatisticsDTO updateWeeklyStatistics(Long id, WeeklyStatisticsDTO weeklyStatisticsDTO) {
        return null;
    }

    @Override
    public WeeklyStatisticsDTO getWeeklyStatisticsById(Long id) {
        return null;
    }

    @Override
    public List<WeeklyStatisticsDTO> getAllWeeklyStatistics() {
        return List.of();
    }

    @Override
    public void saveWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO) {
        // Convert the ReportDTO to ReportEntity
        ReportDTO reportDTO = weeklyStatisticsDTO.getReportDTO();
        UserDTO userDTO = reportDTO.getAdmin();  // Assuming the Report has a User as Admin
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, userEntity);

        // Map WeeklyStatisticsDTO to Entity
        WeeklyStatisticsEntity entity = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, reportEntity);
        weeklyStatisticsRepository.save(entity);
    }

    @Override
    public void updateWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO) {
        // Convert the ReportDTO to ReportEntity
        ReportDTO reportDTO = weeklyStatisticsDTO.getReportDTO();
        UserDTO userDTO = reportDTO.getAdmin();
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, userEntity);

        // Map WeeklyStatisticsDTO to Entity and update
        WeeklyStatisticsEntity entity = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, reportEntity);
        weeklyStatisticsRepository.update(entity);
    }

    @Override
    public void deleteWeeklyStatistics(Long statisticId) {
        weeklyStatisticsRepository.delete(statisticId);
    }

    @Override
    public WeeklyStatisticsDTO findWeeklyStatisticsById(Long statisticId) {
        WeeklyStatisticsEntity entity = weeklyStatisticsRepository.findById(statisticId);
        return WeeklyStatisticsMapper.toDTO(entity);
    }

    @Override
    public List<WeeklyStatisticsDTO> findAllWeeklyStatistics() {
        return weeklyStatisticsRepository.findAll().stream()
                .map(WeeklyStatisticsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WeeklyStatisticsDTO findWeeklyStatisticsByStatisticName(String statisticName) {
        return weeklyStatisticsRepository.findByStatisticName(statisticName)
                .map(WeeklyStatisticsMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("WeeklyStatistics with name " + statisticName + " not found"));
    }
}
