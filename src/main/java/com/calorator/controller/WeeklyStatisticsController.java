package com.calorator.controller;

import com.calorator.dto.WeeklyStatisticsDTO;
import com.calorator.service.UserService;
import com.calorator.service.WeeklyStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-statistics")
public class WeeklyStatisticsController {

    private final WeeklyStatisticsService weeklyStatisticsService;

    private final UserService userService;

    public WeeklyStatisticsController(WeeklyStatisticsService weeklyStatisticsService, UserService userService) {
        this.weeklyStatisticsService = weeklyStatisticsService;
        this.userService = userService;
    }

    @PostMapping("/{reportId}")
    public ResponseEntity<WeeklyStatisticsDTO> createWeeklyStatistics(
            @PathVariable Long reportId,
            @RequestBody WeeklyStatisticsDTO weeklyStatisticsDTO) {
        WeeklyStatisticsDTO createdDTO = weeklyStatisticsService.createWeeklyStatistics(weeklyStatisticsDTO, reportId);
        return ResponseEntity.ok(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyStatisticsDTO> updateWeeklyStatistics(
            @PathVariable Long id,
            @RequestBody WeeklyStatisticsDTO weeklyStatisticsDTO) {
        WeeklyStatisticsDTO updatedDTO = weeklyStatisticsService.updateWeeklyStatistics(id, weeklyStatisticsDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyStatisticsDTO> getWeeklyStatisticsById(@PathVariable Long id) {
        WeeklyStatisticsDTO weeklyStatisticsDTO = weeklyStatisticsService.getWeeklyStatisticsById(id);
        return ResponseEntity.ok(weeklyStatisticsDTO);
    }

    @GetMapping
    public ResponseEntity<List<WeeklyStatisticsDTO>> getAllWeeklyStatistics() {
        List<WeeklyStatisticsDTO> weeklyStatisticsList = weeklyStatisticsService.getAllWeeklyStatistics();
        return ResponseEntity.ok(weeklyStatisticsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeeklyStatistics(@PathVariable Long id) {
        weeklyStatisticsService.deleteWeeklyStatistics(id);
        return ResponseEntity.noContent().build();
    }
}
