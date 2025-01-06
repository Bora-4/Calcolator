package com.calorator.controller;


import com.calorator.dto.FoodEntryDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.FoodEntryService;
import com.calorator.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/food-entries")
public class FoodEntryController {

    private final FoodEntryService foodEntryService;
    private final UserService userService;

    public FoodEntryController(FoodEntryService foodEntryService, UserService userService) {
        this.foodEntryService = foodEntryService;
        this.userService = userService;
    }

    @PostMapping("/save-food-entry")
    public ResponseEntity<String> saveFoodEntry(
            HttpSession session,
            @RequestParam(name = "foodName") String foodName,
            @RequestParam(name = "calories") Integer calories,
            @RequestParam(name = "price") Double price) {

        long userId = (int) session.getAttribute("userId");
        UserDTO user = userService.findById(userId);


        FoodEntryDTO entry = new FoodEntryDTO();
        entry.setUser(user);
        entry.setFoodName(foodName);
        entry.setCalories(calories);
        entry.setPrice(price);

        foodEntryService.save(entry);
        return ResponseEntity.ok("Food entry saved successfully.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<FoodEntryDTO> findFoodEntryById(@PathVariable Long id) {
        FoodEntryDTO foodEntryDTO = foodEntryService.findById(id);
        return ResponseEntity.ok(foodEntryDTO);
    }

    @GetMapping("/last-7-days")
    public ResponseEntity<List<FoodEntryDTO>> getLast7DaysEntries() {
        List<FoodEntryDTO> foodEntries = foodEntryService.findFoodEntriesLast7Days();
        return ResponseEntity.ok(foodEntries);
    }

    @GetMapping("/last-7-days/count")
    public ResponseEntity<Long> getLast7DaysEntriesCount() {
        Long count = foodEntryService.countFoodEntriesLast7Days();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFoodEntry(@PathVariable Long id, @RequestBody FoodEntryDTO foodEntryDTO) {
        foodEntryDTO.setId(id);
        foodEntryService.update(foodEntryDTO);
        return ResponseEntity.ok("Food entry updated successfully.");
    }

    @GetMapping
    public ResponseEntity<List<FoodEntryDTO>> getAllFoodEntries() {
        List<FoodEntryDTO> foodEntries = foodEntryService.findAll();
        return ResponseEntity.ok(foodEntries);
    }

    @GetMapping("/view")
    public String entryPage(){
        return "food-entries";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodEntry(@PathVariable Long id) {
        foodEntryService.delete(id);
        return ResponseEntity.ok("Food entry deleted successfully.");
    }

    @GetMapping("/filter-by-date")
    public ResponseEntity<List<FoodEntryDTO>> getEntriesByDateInterval(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build(); // User is not logged in
        }
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<FoodEntryDTO> foodEntries = foodEntryService.entryDateFiltering(userId, start, end);
        return ResponseEntity.ok(foodEntries);
    }
}
