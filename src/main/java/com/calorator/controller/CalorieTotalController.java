package com.calorator.controller;

import com.calorator.dto.CalorieTotalDTO;
import com.calorator.service.CalorieTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/dashboard")
public class CalorieTotalController {

    @Autowired
    private CalorieTotalService calorieTotalService;

    @GetMapping("/settings/")
    public String getSettingsPage(HttpSession session, Model model) {
        int userId = (int) session.getAttribute("userId");
        Date today = new Date();
        // Retrieve the total calories for the current user for today
        CalorieTotalDTO total = calorieTotalService.getTotalByUserIdAndDate(userId, today);
        model.addAttribute("total", total);
        return "settings";
    }

    @PostMapping("/settings")
    public ResponseEntity<String> createTotal(HttpSession session, @RequestBody CalorieTotalDTO totalDTO){
        int userId = (int) session.getAttribute("userId");
        totalDTO.setUserId(userId);
        CalorieTotalDTO savedTotal = calorieTotalService.saveTotal(totalDTO);
        return ResponseEntity.ok("Total calories created/updated successfully with ID: " + savedTotal.getId());
    }
}
