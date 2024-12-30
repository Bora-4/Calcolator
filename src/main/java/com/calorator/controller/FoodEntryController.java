package com.calorator.controller;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.service.FoodEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/food_entries")
public class FoodEntryController {
    private final FoodEntryService foodEntryService;

    public FoodEntryController(FoodEntryService foodEntryService){
        this.foodEntryService = foodEntryService;
    }

    @PostMapping
    public ResponseEntity<String> saveFoodEntry(@RequestBody FoodEntryDTO foodEntryDTO) {
        foodEntryService.save(foodEntryDTO);
        return ResponseEntity.ok("Food entry saved successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodEntryDTO> findFoodEntryById(@PathVariable Long id) {
        FoodEntryDTO foodEntryDTO = foodEntryService.findById(id);
        return ResponseEntity.ok(foodEntryDTO);
    }

    @GetMapping("/filter-by-date")
    public ResponseEntity<List<FoodEntryDTO>> getEntriesByDateInterval(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        List<FoodEntryDTO> foodEntries = foodEntryService.entryDateFiltering(userId, start, end);
        return ResponseEntity.ok(foodEntries);
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


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodEntry(@PathVariable Long id) {
        foodEntryService.delete(id);
        return ResponseEntity.ok("Food entry deleted successfully.");
    }
}
