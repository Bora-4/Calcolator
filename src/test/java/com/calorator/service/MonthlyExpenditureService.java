package com.calorator.service;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.repository.FoodEntryRepository;
import com.calorator.repository.MonthlyExpenditureRepository;
import com.calorator.service.impl.MonthlyExpenditureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonthlyExpenditureServiceImplTest {

    @InjectMocks
    private MonthlyExpenditureServiceImpl service;

    @Mock
    private FoodEntryRepository foodEntryRepository;

    @Mock
    private MonthlyExpenditureRepository monthlyExpenditureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateMonthlySpending_NoWarning() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 23);
        BigDecimal totalSpent = BigDecimal.valueOf(500);

        when(foodEntryRepository.calculateMonthlySpending(userId, month.getMonthValue(), month.getYear()))
                .thenReturn(totalSpent);
        when(monthlyExpenditureRepository.findByUserIdAndMonth(userId, month)).thenReturn(null);

        service.calculateMonthlySpending(userId, month);

        verify(monthlyExpenditureRepository, times(1)).save(any(MonthlyExpenditureEntity.class));
    }

    @Test
    void testCalculateMonthlySpending_WithWarning() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 23);
        BigDecimal totalSpent = BigDecimal.valueOf(1500);

        when(foodEntryRepository.calculateMonthlySpending(userId, month.getMonthValue(), month.getYear()))
                .thenReturn(totalSpent);
        when(monthlyExpenditureRepository.findByUserIdAndMonth(userId, month)).thenReturn(null);

        service.calculateMonthlySpending(userId, month);

        verify(monthlyExpenditureRepository, times(1)).save(any(MonthlyExpenditureEntity.class));
    }

    @Test
    void testSetCustomLimit_Null() {
        Long userId = 1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.setCustomLimit(userId, null));

        assertEquals("Custom limit must be a positive value.", exception.getMessage());
    }

    @Test
    void testGetMonthlyExpenditure() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 23);
        BigDecimal expectedExpenditure = BigDecimal.valueOf(500);

        when(monthlyExpenditureRepository.calculateMonthlySpending(userId, month.getMonthValue(), month.getYear()))
                .thenReturn(expectedExpenditure);

        BigDecimal result = service.getMonthlyExpenditure(userId, month);

        assertEquals(expectedExpenditure, result);
    }
}
