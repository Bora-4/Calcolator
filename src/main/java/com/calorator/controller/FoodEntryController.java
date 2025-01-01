package com.calorator.controller;


import com.calorator.dto.FoodEntryDTO;
import com.calorator.service.FoodEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/food-entries")
public class FoodEntryController {

    private final FoodEntryService foodEntryService;

    public FoodEntryController(FoodEntryService foodEntryService) {
        this.foodEntryService = foodEntryService;
    }

    // Save a food entry
    @PostMapping
    public ResponseEntity<String> saveFoodEntry(@RequestBody FoodEntryDTO foodEntryDTO) {
        try {
            foodEntryService.validateFoodEntry(foodEntryDTO);
            foodEntryService.save(foodEntryDTO);
            return ResponseEntity.ok("Food entry saved successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Find food entry by ID
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
        try {
            foodEntryService.validateFoodEntry(foodEntryDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
