package com.calorator.controller;

import com.calorator.service.MonthlyExpenditureService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MonthlyExpenditureControllerTest {

    @Mock
    private MonthlyExpenditureService monthlyExpenditureService;

    @InjectMocks
    private MonthlyExpenditureController monthlyExpenditureController;

    @Test
    void testCalculateMonthlySpending_Success() {

        Long userId = 1L;
        String month = "2025-01";

        doNothing().when(monthlyExpenditureService).calculateMonthlySpending(userId, LocalDate.parse("2025-01-01"));

        ResponseEntity<String> response = monthlyExpenditureController.calculateMonthlySpending(userId, month);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Monthly spending calculated successfully.", response.getBody());


        verify(monthlyExpenditureService, times(1)).calculateMonthlySpending(userId, LocalDate.parse("2025-01-01"));
    }

    @Test
    void testCalculateMonthlySpending_Failure() {

        Long userId = 1L;
        String month = "2025-01";


        doThrow(new RuntimeException("Some error")).when(monthlyExpenditureService).calculateMonthlySpending(userId, LocalDate.parse("2025-01-01"));


        ResponseEntity<String> response = monthlyExpenditureController.calculateMonthlySpending(userId, month);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Error calculating monthly spending"));


        verify(monthlyExpenditureService, times(1)).calculateMonthlySpending(userId, LocalDate.parse("2025-01-01"));
    }

    @Test
    void testSetCustomLimit_Success() {

        Long userId = 1L;
        BigDecimal customLimit = new BigDecimal("500.00");

        doNothing().when(monthlyExpenditureService).setCustomLimit(userId, customLimit);

        ResponseEntity<String> response = monthlyExpenditureController.setCustomLimit(userId, customLimit);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Custom spending limit set successfully.", response.getBody());

        verify(monthlyExpenditureService, times(1)).setCustomLimit(userId, customLimit);
    }

    @Test
    void testSetCustomLimit_Failure() {

        Long userId = 1L;
        BigDecimal customLimit = new BigDecimal("-500.00");

        doThrow(new IllegalArgumentException("Invalid custom limit")).when(monthlyExpenditureService).setCustomLimit(userId, customLimit);

        ResponseEntity<String> response = monthlyExpenditureController.setCustomLimit(userId, customLimit);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid custom limit"));

        verify(monthlyExpenditureService, times(1)).setCustomLimit(userId, customLimit);
    }
}
