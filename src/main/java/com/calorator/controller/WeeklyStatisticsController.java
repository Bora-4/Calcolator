package com.calorator.controller;

import com.calorator.dto.UserDTO;
import com.calorator.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports/weekly")
public class WeeklyStatisticsController {

    private final ReportService reportService;

    public WeeklyStatisticsController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Endpoint to get the weekly statistics for all users
    @GetMapping
    public ResponseEntity<String> getWeeklyStatistics() {
        try {
            //Needs a method getWeeklyStatisticsReport to be created in ReportService
            String weeklyReport = reportService.getWeeklyStatisticsReport();
            return ResponseEntity.ok(weeklyReport);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\":\"Error generating report: " + e.getMessage() + "\"}");
        }
    }

    // Endpoint to get a list of users exceeding their monthly price limit
    @GetMapping("/exceeding-users")
    public ResponseEntity<List<UserDTO>> getExceedingUsers() {
        try {
            //Needs a method getUsersExceedingMonthlyPriceLimit to be created in ReportService
            List<UserDTO> exceedingUsers = reportService.getUsersExceedingMonthlyPriceLimit();
            return ResponseEntity.ok(exceedingUsers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint to get the total calories consumed in the past week
    @GetMapping("/total-calories")
    public ResponseEntity<Integer> getTotalCaloriesLastWeek() {
        try {
            //Needs a method getTotalCaloriesLastWeek to be created in ReportService
            int totalCalories = reportService.getTotalCaloriesLastWeek();
            return ResponseEntity.ok(totalCalories);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint to get the average calories consumed per user in the past week
    @GetMapping("/average-calories")
    public ResponseEntity<Double> getAverageCaloriesPerUserLastWeek() {
        try {
            //Needs a method getAverageCaloriesPerUserLastWeek to be created in ReportService
            double averageCalories = reportService.getAverageCaloriesPerUserLastWeek();
            return ResponseEntity.ok(averageCalories);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
