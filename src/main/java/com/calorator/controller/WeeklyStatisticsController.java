package com.calorator.controller;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.service.UserService;
import com.calorator.service.WeeklyStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly-statistics")
public class WeeklyStatisticsController {

    private final WeeklyStatisticsService weeklyStatisticsService;

    public WeeklyStatisticsController(WeeklyStatisticsService weeklyStatisticsService) {
        this.weeklyStatisticsService = weeklyStatisticsService;
    }

    @PostMapping
    public ResponseEntity<String> createWeeklyStatistics(@RequestBody WeeklyStatisticsDTO weeklyStatisticsDTO) {
        try {
            weeklyStatisticsService.saveWeeklyStatistics(weeklyStatisticsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Weekly statistics created successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateWeeklyStatistics(@RequestBody WeeklyStatisticsDTO weeklyStatisticsDTO) {
        try {
            WeeklyStatisticsDTO weeklyStatistics = weeklyStatisticsService.findWeeklyStatisticsById(weeklyStatisticsDTO.getId());
            if (weeklyStatistics == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"Weekly statistics not found.\"}");
            }
            if (weeklyStatisticsDTO.getStatisticName() != null) {
                weeklyStatistics.setStatisticName(weeklyStatisticsDTO.getStatisticName());
            }
            if (weeklyStatisticsDTO.getStatisticValue() != 0) {
                weeklyStatistics.setStatisticValue(weeklyStatisticsDTO.getStatisticValue());
            }
            if (weeklyStatisticsDTO.getReportDTO() != null) {
                weeklyStatistics.setReportDTO(weeklyStatisticsDTO.getReportDTO());
            }
            weeklyStatisticsService.updateWeeklyStatistics(weeklyStatisticsDTO);
            return ResponseEntity.ok("{\"message\":\"Weekly statistics updated successfully.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping
    public ResponseEntity<List<WeeklyStatisticsDTO>> getAllWeeklyStatistics() {
        try {
            List<WeeklyStatisticsDTO> weeklyStatisticsList = weeklyStatisticsService.findAllWeeklyStatistics();
            return ResponseEntity.ok(weeklyStatisticsList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteWeeklyStatistics(@PathVariable("id") Long id) {
        try {
            weeklyStatisticsService.deleteWeeklyStatistics(id);
            return ResponseEntity.ok("{\"message\":\"Weekly statistics deleted successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<WeeklyStatisticsDTO> getWeeklyStatisticsById(@PathVariable("id") Long id) {
        try {
            WeeklyStatisticsDTO weeklyStatistics = weeklyStatisticsService.findWeeklyStatisticsById(id);
            return ResponseEntity.ok(weeklyStatistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}