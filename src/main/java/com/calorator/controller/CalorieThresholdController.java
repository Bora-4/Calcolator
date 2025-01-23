package com.calorator.controller;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.service.CalorieThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/calorie-threshold")
public class CalorieThresholdController {

    @Autowired
    private CalorieThresholdService calorieThresholdService;

    @GetMapping("/{date}")
    public CalorieThresholdDTO getThresholdByUserIdAndDate(@PathVariable Date date, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return calorieThresholdService.getThresholdByUserIdAndDate(userId, date);
    }

    @PostMapping
    public CalorieThresholdDTO saveThreshold(@RequestBody CalorieThresholdDTO thresholdDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        thresholdDTO.getUser().setId(userId);
        return calorieThresholdService.saveThreshold(thresholdDTO);
    }

    @PutMapping("/{date}/calories")
    public void updateTotalCalories(@RequestBody int calories, @PathVariable Date date, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        calorieThresholdService.updateTotalCalories(userId, calories, date);
    }

    @GetMapping("/{dateInMillis}/exceeded")
    public boolean isThresholdExceeded(@PathVariable long dateInMillis, HttpSession session) {
        Date date = new Date(dateInMillis);
        Long userId = (Long) session.getAttribute("userId");
        return calorieThresholdService.isThresholdExceeded(userId, date);
    }

    @GetMapping("/exceeded-dates")
    public List<Date> getExceededThresholdDates(@RequestParam Date startDate, @RequestParam Date endDate, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return calorieThresholdService.getExceededThresholdDates(userId, startDate, endDate);
    }
}
