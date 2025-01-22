package com.calorator.controller;


import com.calorator.dto.FoodEntryDTO;
import com.calorator.service.CalorieThresholdService;
import com.calorator.service.FoodEntryService;
import com.calorator.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/food-entries")
public class FoodEntryController {

    private final FoodEntryService foodEntryService;
    private final UserService userService;
    private final CalorieThresholdService calorieThresholdService;

    public FoodEntryController(FoodEntryService foodEntryService, UserService userService, CalorieThresholdService calorieThresholdService) {
        this.foodEntryService = foodEntryService;
        this.userService = userService;
        this.calorieThresholdService = calorieThresholdService;
    }

    // Save a food entry
    @PostMapping("/save")
    public ResponseEntity<String> saveFoodEntry(
            @RequestBody FoodEntryDTO foodEntryDTO,
            @RequestParam(required = false) Long userId,
            HttpSession session) {
        try {
            Long effectiveUserId = (userId != null) ? userId : (Long) session.getAttribute("userId");
            if (effectiveUserId == null) {
                return ResponseEntity.status(401).build(); // User not logged in or ID not provided
            }
            foodEntryDTO.setUser(userService.findById(effectiveUserId));
            foodEntryDTO.setEntryDate(LocalDateTime.now());
            foodEntryService.validateFoodEntry(foodEntryDTO);
            calorieThresholdService.updateTotalCalories(effectiveUserId, foodEntryDTO.getCalories(), new Date());
            foodEntryService.save(foodEntryDTO);
            return ResponseEntity.ok("{\"message\":\"Food entry saved successfully.\"}");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Find food entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodEntryDTO> findFoodEntryById(@PathVariable Long id) {
        try {
            FoodEntryDTO foodEntryDTO = foodEntryService.findById(id);
            return ResponseEntity.ok(foodEntryDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/last-7-days")
    public ResponseEntity<List<FoodEntryDTO>> getLast7DaysEntries(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(401).build();
            }
            List<FoodEntryDTO> foodEntries = foodEntryService.findFoodEntriesLast7Days(userId);
            return ResponseEntity.ok(foodEntries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/last-7-days/count")
    public ResponseEntity<Long> getLast7DaysEntriesCount() {
        Long count = foodEntryService.countFoodEntriesLast7Days();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodEntry(@PathVariable Long id, @RequestBody FoodEntryDTO foodEntryDTO) {
        try {
            foodEntryDTO.setId(id);
            foodEntryService.validateFoodEntry(foodEntryDTO);

            FoodEntryDTO existingEntry = foodEntryService.findById(id);
            Long userId = existingEntry.getUser().getId();
            int calorieDifference = foodEntryDTO.getCalories() - existingEntry.getCalories();
            LocalDateTime localDateTime = foodEntryDTO.getEntryDate();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            calorieThresholdService.updateTotalCalories(userId, calorieDifference, date);

            foodEntryService.update(foodEntryDTO);
            return ResponseEntity.ok("{\"message\":\"Food entry updated successfully.\"}");
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping
    public ResponseEntity<List<FoodEntryDTO>> getAllFoodEntries() {
        List<FoodEntryDTO> foodEntries = foodEntryService.findAll();
        return ResponseEntity.ok(foodEntries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodEntry(@PathVariable Long id) {

        FoodEntryDTO existingEntry = foodEntryService.findById(id);
        Long userId = existingEntry.getUser().getId();
        int calories = existingEntry.getCalories();
        LocalDateTime localDateTime = existingEntry.getEntryDate();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        foodEntryService.delete(id);

        calorieThresholdService.updateTotalCalories(userId, -calories, date);

        return ResponseEntity.ok("{\"message\":\"Food entry deleted successfully.\"}");
    }

    @GetMapping("/filter-by-date")
    public ResponseEntity<List<FoodEntryDTO>> getEntriesByDateInterval(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpSession session) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(401).build();
            }
            List<FoodEntryDTO> foodEntries = foodEntryService.entryDateFiltering(userId, start, end);
            return ResponseEntity.ok(foodEntries);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
