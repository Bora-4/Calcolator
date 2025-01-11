package com.calorator.controller;

import com.calorator.service.MonthlyExpenditureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/expenditure")
public class MonthlyExpenditureController {

    private final MonthlyExpenditureService monthlyExpenditureService;

    public MonthlyExpenditureController(MonthlyExpenditureService monthlyExpenditureService) {
        this.monthlyExpenditureService = monthlyExpenditureService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateMonthlySpending(@RequestParam Long userId, @RequestParam String month) {

        try {
            LocalDate monthDate = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            monthlyExpenditureService.calculateMonthlySpending(userId, monthDate);
            return ResponseEntity.ok("Monthly spending calculated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error calculating monthly spending: " + e.getMessage());
        }
    }

    @PostMapping("/set-limit")
    public ResponseEntity<String> setCustomLimit(
            @RequestParam Long userId,
            @RequestParam BigDecimal customLimit) {

        try {
            monthlyExpenditureService.setCustomLimit(userId, customLimit);
            return ResponseEntity.ok("Custom spending limit set successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid custom limit: " + e.getMessage());
        }
    }

}
