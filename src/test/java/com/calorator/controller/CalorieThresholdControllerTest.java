package com.calorator.controller;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.CalorieThresholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpSession;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalorieThresholdControllerTest {

    @InjectMocks
    private CalorieThresholdController calorieThresholdController;

    @Mock
    private CalorieThresholdService calorieThresholdService;

    private MockHttpSession session;

    private final Long userId = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
        session.setAttribute("userId", userId);
    }

    @Test
    public void testGetThresholdByUserIdAndDate() {

        Date date = new Date();
        CalorieThresholdDTO expectedThreshold = new CalorieThresholdDTO();
        when(calorieThresholdService.getThresholdByUserIdAndDate(userId, date)).thenReturn(expectedThreshold);

        CalorieThresholdDTO actualThreshold = calorieThresholdController.getThresholdByUserIdAndDate(date, session);

        assertEquals(expectedThreshold, actualThreshold);
        verify(calorieThresholdService, times(1)).getThresholdByUserIdAndDate(userId, date);
    }

    @Test
    public void testSaveThreshold() {

        CalorieThresholdDTO thresholdDTO = new CalorieThresholdDTO();
        thresholdDTO.setUser(new UserDTO());
        CalorieThresholdDTO savedThreshold = new CalorieThresholdDTO();
        when(calorieThresholdService.saveThreshold(thresholdDTO)).thenReturn(savedThreshold);

        CalorieThresholdDTO result = calorieThresholdController.saveThreshold(thresholdDTO, session);

        assertEquals(savedThreshold, result);
        assertEquals(userId, thresholdDTO.getUser().getId());
        verify(calorieThresholdService, times(1)).saveThreshold(thresholdDTO);
    }

    @Test
    public void testUpdateTotalCalories() {
        int calories = 500;
        Date date = new Date();

        calorieThresholdController.updateTotalCalories(calories, date, session);

        verify(calorieThresholdService, times(1)).updateTotalCalories(userId, calories, date);
    }

    @Test
    public void testIsThresholdExceeded() {

        Date date = new Date();
        when(calorieThresholdService.isThresholdExceeded(userId, date)).thenReturn(true);

        boolean isExceeded = calorieThresholdController.isThresholdExceeded(date, session);

        assertTrue(isExceeded);
        verify(calorieThresholdService, times(1)).isThresholdExceeded(userId, date);
    }

    @Test
    public void testGetExceededThresholdDates() {

        Date startDate = new Date(System.currentTimeMillis() - 86400000L); // 1 day ago
        Date endDate = new Date();
        List<Date> exceededDates = Arrays.asList(startDate, endDate);
        when(calorieThresholdService.getExceededThresholdDates(userId, startDate, endDate)).thenReturn(exceededDates);

        List<Date> result = calorieThresholdController.getExceededThresholdDates(startDate, endDate, session);

        assertEquals(exceededDates, result);
        verify(calorieThresholdService, times(1)).getExceededThresholdDates(userId, startDate, endDate);
    }
}
