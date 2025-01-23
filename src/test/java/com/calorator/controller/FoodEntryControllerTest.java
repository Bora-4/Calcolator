package com.calorator.controller;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.CalorieThresholdService;
import com.calorator.service.FoodEntryService;
import com.calorator.service.MonthlyExpenditureService;
import com.calorator.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FoodEntryControllerTest {

    @InjectMocks
    private FoodEntryController foodEntryController;

    @Mock
    private FoodEntryService foodEntryService;

    @Mock
    private UserService userService;

    @Mock
    private CalorieThresholdService calorieThresholdService;

    @Mock
    private MonthlyExpenditureService monthlyExpenditureService;

    private HttpSession session;
    private Long userId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        session = mock(HttpSession.class);
        userId = 1L;
        when(session.getAttribute("userId")).thenReturn(userId);
    }

    @Test
    public void testSaveFoodEntry() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setCalories(500);

        when(userService.findById(userId)).thenReturn(foodEntryDTO.getUser());

        ResponseEntity<String> response = foodEntryController.saveFoodEntry(foodEntryDTO, userId, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Food entry saved successfully.\"}", response.getBody());

        verify(foodEntryService, times(1)).save(foodEntryDTO);
        verify(calorieThresholdService, times(1)).updateTotalCalories(eq(userId), eq(500), any(Date.class));
        verify(monthlyExpenditureService, times(1)).calculateMonthlySpending(eq(userId), any(LocalDate.class));
    }

    @Test
    public void testFindFoodEntryById() {
        Long id = 1L;
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        when(foodEntryService.findById(id)).thenReturn(foodEntryDTO);

        ResponseEntity<FoodEntryDTO> response = foodEntryController.findFoodEntryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodEntryDTO, response.getBody());
    }

    @Test
    public void testGetLast7DaysEntries() {
        List<FoodEntryDTO> foodEntries = List.of(new FoodEntryDTO());
        when(foodEntryService.findFoodEntriesLast7Days(userId)).thenReturn(foodEntries);

        ResponseEntity<List<FoodEntryDTO>> response = foodEntryController.getLast7DaysEntries(session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodEntries, response.getBody());
    }

    @Test
    public void testUpdateFoodEntry() {
        Long id = 1L;
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setCalories(300);
        foodEntryDTO.setEntryDate(LocalDateTime.now());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        FoodEntryDTO existingEntry = new FoodEntryDTO();
        existingEntry.setCalories(200);
        existingEntry.setUser(userDTO);
        existingEntry.setEntryDate(LocalDateTime.now());
        when(foodEntryService.findById(id)).thenReturn(existingEntry);

        ResponseEntity<String> response = foodEntryController.updateFoodEntry(id, foodEntryDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Food entry updated successfully.\"}", response.getBody());

        verify(calorieThresholdService, times(1)).updateTotalCalories(eq(userId), eq(100), any(Date.class));
        verify(foodEntryService, times(1)).update(foodEntryDTO);
    }


    @Test
    public void testGetAllFoodEntries() {
        List<FoodEntryDTO> foodEntries = List.of(new FoodEntryDTO());
        when(foodEntryService.findAll()).thenReturn(foodEntries);

        ResponseEntity<List<FoodEntryDTO>> response = foodEntryController.getAllFoodEntries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodEntries, response.getBody());
    }

    @Test
    public void testDeleteFoodEntry() {
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        FoodEntryDTO existingEntry = new FoodEntryDTO();
        existingEntry.setCalories(200);
        existingEntry.setUser(userDTO);
        existingEntry.setEntryDate(LocalDateTime.now());

        when(foodEntryService.findById(id)).thenReturn(existingEntry);

        ResponseEntity<String> response = foodEntryController.deleteFoodEntry(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\":\"Food entry deleted successfully.\"}", response.getBody());

        verify(calorieThresholdService, times(1)).updateTotalCalories(eq(userId), eq(-200), any(Date.class));
        verify(foodEntryService, times(1)).delete(id);
    }



    @Test
    public void testGetEntriesByDateInterval() {
        String startDate = "2025-01-01T00:00:00";
        String endDate = "2025-01-07T23:59:59";
        List<FoodEntryDTO> foodEntries = List.of(new FoodEntryDTO());

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        when(foodEntryService.entryDateFiltering(eq(userId), eq(start), eq(end))).thenReturn(foodEntries);

        ResponseEntity<List<FoodEntryDTO>> response = foodEntryController.getEntriesByDateInterval(startDate, endDate, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodEntries, response.getBody());
    }
}
