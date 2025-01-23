package com.calorator.service.impl;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.UserDTO;
import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.ReportMapper;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.ReportRepository;
import com.calorator.service.FoodEntryService;
import com.calorator.service.ReportService;
import com.calorator.service.UserService;
import com.calorator.service.WeeklyStatisticsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final FoodEntryService foodEntryService;
    private final WeeklyStatisticsService weeklyStatisticsService;
    private final UserService userService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, FoodEntryService foodEntryService, WeeklyStatisticsService weeklyStatisticsService, UserService userService) {
        this.reportRepository = reportRepository;
        this.foodEntryService = foodEntryService;
        this.weeklyStatisticsService = weeklyStatisticsService;
        this.userService = userService;
    }
    @Override
    public ReportDTO save(ReportDTO reportDTO) {
        UserDTO admin = reportDTO.getAdmin();
        UserEntity adminEntity = null;
        if (admin != null) {
            adminEntity = UserMapper.toEntity(admin);
        }
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, adminEntity);

        // Save the ReportEntity first to ensure it's persisted
        reportRepository.save(reportEntity);

        // Retrieve the saved ReportDTO to get the persisted report entity with its ID
        ReportDTO savedReportDTO = ReportMapper.toDTO(reportEntity);

        // Save "Nr of entries" statistic
        WeeklyStatisticsDTO weekEntries = new WeeklyStatisticsDTO();
        weekEntries.setReportDTO(savedReportDTO);
        weekEntries.setStatisticName("Nr of entries");
        weekEntries.setStatisticValue(foodEntryService.countFoodEntriesLast7Days());
        weeklyStatisticsService.saveWeeklyStatistics(weekEntries);

        // Calculate and save statistics for each user
        List<UserDTO> users = userService.findAll();
        for (UserDTO user : users) {
            WeeklyStatisticsDTO weekCalories = new WeeklyStatisticsDTO();
            weekCalories.setReportDTO(savedReportDTO);
            weekCalories.setStatisticName("Average nr of calories per user");
            weekCalories.setStatisticValue(foodEntryService.calculateAverageCaloriesPerUserLast7Days(user.getId()));
            weeklyStatisticsService.saveWeeklyStatistics(weekCalories);
        }
        return savedReportDTO;
    }



    @Override
    public ReportDTO findById(Long id) {
        ReportEntity reportEntity = reportRepository.findById(id);
        if (reportEntity != null) {
            return ReportMapper.toDTO(reportEntity);
        }
        throw new EntityNotFoundException("Report with id " + id + " was not found.");
    }

    @Override
    public List<ReportDTO> findAll() {
        return reportRepository.findAll().stream()
                .map(ReportMapper::toDTO)
                .toList();
    }

    @Override
    public void update(ReportDTO reportDTO) {
        ReportEntity existingReport = reportRepository.findById(reportDTO.getId());
        if (existingReport != null) {
            ReportEntity updatedReport = ReportMapper.toEntity(reportDTO, existingReport.getAdmin());
            reportRepository.update(updatedReport);
        } else {
            throw new EntityNotFoundException("Report with id " + reportDTO.getId() + " was not found.");
        }
    }

    @Override
    public void delete(Long id) {
        ReportEntity reportEntity = reportRepository.findById(id);
        if (reportEntity == null) {
            throw new EntityNotFoundException("Report with id " + id + " was not found.");
        }
        reportRepository.delete(id);
    }

    @Override
    public ReportDTO findByReportDate(String reportDate) {
        if (reportDate == null || reportDate.isEmpty()) {
            throw new IllegalArgumentException("Report date cannot be null or empty.");
        }
        ReportEntity reportEntity = reportRepository.findByReportDate(reportDate);
        if (reportEntity == null) {
            throw new IllegalArgumentException("No report found for the given date: " + reportDate);
        }
        return ReportMapper.toDTO(reportEntity);
    }

    @Override
    public List<WeeklyStatisticsDTO> getWeeklyStatistics(Long reportId) {
        List<WeeklyStatisticsDTO> allStatistics = weeklyStatisticsService.findAllWeeklyStatistics();
        return allStatistics.stream()
                .filter(statistic -> statistic.getReportDTO().getId().equals(reportId))
                .collect(Collectors.toList());
    }


}
