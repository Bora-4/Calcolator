package com.calorator.controller;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.CalorieThresholdService;
import com.calorator.service.FoodEntryService;
import com.calorator.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoodEntryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FoodEntryService foodEntryService;

    @Mock
    private UserService userService;

    @Mock
    private CalorieThresholdService calorieThresholdService;

    @InjectMocks
    private FoodEntryController foodEntryController;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
        mockMvc = MockMvcBuilders.standaloneSetup(foodEntryController).build();
    }

    @Test
    public void saveFoodEntry_ShouldReturn200_WhenSuccessful() throws Exception {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setCalories(500);

        // Mock user service to return a valid UserDTO with an ID
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.findById(1L)).thenReturn(userDTO);

        doNothing().when(foodEntryService).save(any(FoodEntryDTO.class));  // Mock void save method
        doNothing().when(calorieThresholdService).updateTotalCalories(anyLong(), anyInt(), any());  // Mock calorieThresholdService method

        session.setAttribute("userId", 1L);  // Set the session attribute

        mockMvc.perform(post("/food-entries/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"calories\": 500, \"foodName\": \"Apple\"}")
                        .session(session))  // Use MockHttpSession
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Food entry saved successfully."));
    }

    @Test
    public void findFoodEntryById_ShouldReturnFoodEntry_WhenFound() throws Exception {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setCalories(500);

        when(foodEntryService.findById(1L)).thenReturn(foodEntryDTO);

        mockMvc.perform(get("/food-entries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calories").value(500));
    }

    @Test
    public void getLast7DaysEntries_ShouldReturnEntries_WhenLoggedIn() throws Exception {
        // Mock the session and service
        session.setAttribute("userId", 1L);
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setCalories(500);  // Mock a valid food entry
        when(foodEntryService.findFoodEntriesLast7Days(1L)).thenReturn(Collections.singletonList(foodEntryDTO));

        mockMvc.perform(get("/food-entries/last-7-days")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())  // Expecting an array in the response
                .andExpect(jsonPath("$[0].calories").value(500));  // Verify the first entry's calories
    }

    @Test
    public void updateFoodEntry_ShouldReturn401_WhenSessionIsMissing() throws Exception {
        // No session attribute set for the user
        mockMvc.perform(put("/food-entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"calories\": 600, \"foodName\": \"Banana\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("User not authenticated."));
    }


    @Test
    public void deleteFoodEntry_ShouldReturn401_WhenSessionIsMissing() throws Exception {
        // No session attribute set for the user
        mockMvc.perform(delete("/food-entries/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("User not authenticated."));
    }


    @Test
    public void getEntriesByDateInterval_ShouldReturn200_WhenSuccessful() throws Exception {
        session.setAttribute("userId", 1L);  // Set the session attribute
        mockMvc.perform(get("/food-entries/filter-by-date")
                        .param("startDate", "2025-01-01T00:00:00")
                        .param("endDate", "2025-01-07T23:59:59")
                        .session(session))
                .andExpect(status().isOk());
    }
}
