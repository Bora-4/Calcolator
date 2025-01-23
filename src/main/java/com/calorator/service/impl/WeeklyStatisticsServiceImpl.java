package com.calorator.service.impl;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.dto.UserDTO;
import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.WeeklyStatisticsMapper;
import com.calorator.mapper.ReportMapper;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.WeeklyStatisticsRepository;
import com.calorator.service.FoodEntryService;
import com.calorator.service.WeeklyStatisticsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WeeklyStatisticsServiceImpl implements WeeklyStatisticsService {

    private final WeeklyStatisticsRepository weeklyStatisticsRepository;
    private final FoodEntryService foodEntryService;

    @Autowired
    public WeeklyStatisticsServiceImpl(WeeklyStatisticsRepository weeklyStatisticsRepository, FoodEntryService foodEntryService) {
        this.weeklyStatisticsRepository = weeklyStatisticsRepository;
        this.foodEntryService = foodEntryService;
    }

    @Override
    public void saveWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO) {
        UserDTO userDTO = weeklyStatisticsDTO.getReportDTO().getAdmin();
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        ReportEntity reportEntity = ReportMapper.toEntity(weeklyStatisticsDTO.getReportDTO(), userEntity);
        WeeklyStatisticsEntity entity = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, reportEntity);

        weeklyStatisticsRepository.save(entity);
    }

    @Override
    public WeeklyStatisticsDTO findWeeklyStatisticsById(Long statisticId) {
        WeeklyStatisticsEntity entity = weeklyStatisticsRepository.findById(statisticId);
        if (entity != null) {
            return WeeklyStatisticsMapper.toDTO(entity);
        }
        throw new EntityNotFoundException("WeeklyStatistics with id " + statisticId + " was not found.");
    }

    @Override
    public List<WeeklyStatisticsDTO> findAllWeeklyStatistics() {
        return weeklyStatisticsRepository.findAll().stream()
                .map(WeeklyStatisticsMapper::toDTO)
                .toList();
    }

    @Override
    public void updateWeeklyStatistics(WeeklyStatisticsDTO weeklyStatisticsDTO) {
        WeeklyStatisticsEntity existingWeeklyStatistics = weeklyStatisticsRepository.findById(weeklyStatisticsDTO.getId());
        if (existingWeeklyStatistics != null) {
            WeeklyStatisticsEntity updatedWeeklyStatistics = WeeklyStatisticsMapper.toEntity(weeklyStatisticsDTO, existingWeeklyStatistics.getReport());
            weeklyStatisticsRepository.update(updatedWeeklyStatistics);
        } else {
            throw new EntityNotFoundException("WeeklyStatistics with id " + weeklyStatisticsDTO.getId() + " was not found.");
        }
    }

    @Override
    public void deleteWeeklyStatistics(Long id) {
        WeeklyStatisticsEntity weeklyStatisticsEntity = weeklyStatisticsRepository.findById(id);
        if (weeklyStatisticsEntity == null) {
            throw new EntityNotFoundException("WeeklyStatistics with id " + id + " was not found.");
        }
        weeklyStatisticsRepository.delete(id);
    }
}