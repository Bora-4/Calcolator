package com.calorator.controller;

import com.calorator.dto.UserSpendingDTO;
import com.calorator.service.UserSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-spending")
public class UserSpendingController {

    private final UserSpendingService userSpendingService;

    @Autowired
    public UserSpendingController(UserSpendingService userSpendingService) {
        this.userSpendingService = userSpendingService;
    }

    @PostMapping
    public ResponseEntity<Void> saveUserSpending(@RequestBody UserSpendingDTO userSpendingDTO) {
        userSpendingService.saveUserSpending(userSpendingDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUserSpending(@RequestBody UserSpendingDTO userSpendingDTO) {
        userSpendingService.updateUserSpending(userSpendingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSpending(@PathVariable Long id) {
        userSpendingService.deleteUserSpending(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSpendingDTO> findUserSpendingById(@PathVariable Long id) {
        UserSpendingDTO dto = userSpendingService.findUserSpendingById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserSpendingDTO>> findAllUserSpendings() {
        List<UserSpendingDTO> spendings = userSpendingService.findAllUserSpendings();
        return ResponseEntity.ok(spendings);
    }
}
