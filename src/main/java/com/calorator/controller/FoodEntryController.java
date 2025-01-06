package com.calorator.controller;


import com.calorator.dto.FoodEntryDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.CalorieTotalService;
import com.calorator.service.FoodEntryService;
import com.calorator.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/food-entries")
public class FoodEntryController {

    private final FoodEntryService foodEntryService;
    private final UserService userService;
    private final CalorieTotalService calorieTotalService;

    public FoodEntryController(FoodEntryService foodEntryService, UserService userService, CalorieTotalService calorieTotalService) {
        this.foodEntryService = foodEntryService;
        this.userService = userService;
        this.calorieTotalService = calorieTotalService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFoodEntry(
            HttpSession session,
            @RequestBody FoodEntryDTO entry) {

        int userId = (int) session.getAttribute("userId");
        UserDTO user = userService.findById(userId);

        entry.setUser(user);
        entry.setEntryDate(LocalDateTime.now());
        calorieTotalService.updateTotalCalories(userId, entry.getCalories(), new Date());
        foodEntryService.save(entry);
        return ResponseEntity.ok("Food entry saved successfully.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<FoodEntryDTO> findFoodEntryById(@PathVariable int id) {
        FoodEntryDTO foodEntryDTO = foodEntryService.findById(id);
        return ResponseEntity.ok(foodEntryDTO);
    }

    @GetMapping("/last-7-days")
    public ResponseEntity<List<FoodEntryDTO>> getLast7DaysEntries() {
        List<FoodEntryDTO> foodEntries = foodEntryService.findFoodEntriesLast7Days();
        return ResponseEntity.ok(foodEntries);
    }

    @GetMapping("/last-7-days/count")
    public ResponseEntity<Integer> getLast7DaysEntriesCount() {
        int count = foodEntryService.countFoodEntriesLast7Days();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodEntry(@PathVariable int id, @RequestBody FoodEntryDTO foodEntryDTO) {
        foodEntryDTO.setId(id);
        foodEntryService.update(foodEntryDTO);
        return ResponseEntity.ok("Food entry updated successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FoodEntryDTO>> getAllFoodEntries() {
        List<FoodEntryDTO> foodEntries = foodEntryService.findAll();
        return ResponseEntity.ok(foodEntries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodEntry(@PathVariable int id) {
        foodEntryService.delete(id);
        return ResponseEntity.ok("Food entry deleted successfully.");
    }

    @GetMapping("/filter-by-date")
    public ResponseEntity<List<FoodEntryDTO>> getEntriesByDateInterval(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        if (userId == 0) {
            return ResponseEntity.status(401).build(); // User is not logged in
        }
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<FoodEntryDTO> foodEntries = foodEntryService.entryDateFiltering(userId, start, end);
        return ResponseEntity.ok(foodEntries);
    }
}
