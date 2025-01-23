package com.calorator.controller;

import com.calorator.service.MonthlyExpenditureService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MonthlyExpenditureControllerTest {

    @Mock
    private MonthlyExpenditureService monthlyExpenditureService;

    @InjectMocks
    private MonthlyExpenditureController monthlyExpenditureController;

    @Mock
    private HttpSession session;

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
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Error calculating monthly spending"));

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
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Invalid custom limit"));

        verify(monthlyExpenditureService, times(1)).setCustomLimit(userId, customLimit);
    }

    @Test
    void testGetMonthlyExpenditure_Success() {
        Long userId = 1L;
        BigDecimal expenditure = BigDecimal.valueOf(500);
        LocalDate monthDate = LocalDate.now();

        when(session.getAttribute("userId")).thenReturn(userId);
        when(monthlyExpenditureService.getMonthlyExpenditure(userId, monthDate)).thenReturn(expenditure);

        ResponseEntity<BigDecimal> response = monthlyExpenditureController.getMonthlyExpenditure(session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expenditure, response.getBody());

        verify(session, times(1)).getAttribute("userId");
        verify(monthlyExpenditureService, times(1)).getMonthlyExpenditure(userId, monthDate);
    }

    @Test
    void testGetMonthlyExpenditure_NoUserId() {
        when(session.getAttribute("userId")).thenReturn(null);

        ResponseEntity<BigDecimal> response = monthlyExpenditureController.getMonthlyExpenditure(session);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());

        verify(session, times(1)).getAttribute("userId");
        verify(monthlyExpenditureService, never()).getMonthlyExpenditure(anyLong(), any(LocalDate.class));
    }

    @Test
    void testGetMonthlyExpenditure_NotFound() {
        Long userId = 1L;
        LocalDate monthDate = LocalDate.now();

        when(session.getAttribute("userId")).thenReturn(userId);
        when(monthlyExpenditureService.getMonthlyExpenditure(userId, monthDate)).thenReturn(null);

        ResponseEntity<BigDecimal> response = monthlyExpenditureController.getMonthlyExpenditure(session);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(session, times(1)).getAttribute("userId");
        verify(monthlyExpenditureService, times(1)).getMonthlyExpenditure(userId, monthDate);
    }
}
