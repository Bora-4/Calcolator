package com.calorator.controller;

import com.calorator.service.MonthlyExpenditureService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/get-Monthly-Expenditure")
    public ResponseEntity<BigDecimal> getMonthlyExpenditure(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        LocalDate monthDate = LocalDate.now();
        System.out.println("UserId: " + userId + ", Date: " + monthDate);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        var expenditure = monthlyExpenditureService.getMonthlyExpenditure(userId, monthDate);
        System.out.println("Expenditure: " + expenditure);

        if (expenditure == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(expenditure);
    }

}
