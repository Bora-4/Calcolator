package com.calorator.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CalorieThresholdEntityTest {

    private CalorieThresholdEntity calorieThresholdEntity;

    @BeforeEach
    void setUp() {
        calorieThresholdEntity = new CalorieThresholdEntity();
    }

    @Test
    void testThresholdId() {
        calorieThresholdEntity.setThresholdId(1);
        assertEquals(1, calorieThresholdEntity.getThresholdId());
    }

    @Test
    void testUser() {
        UserEntity user = new UserEntity();
        calorieThresholdEntity.setUser(user);
        assertEquals(user, calorieThresholdEntity.getUser());
    }

    @Test
    void testThresholdDate() {
        Date date = new Date();
        calorieThresholdEntity.setThresholdDate(date);
        assertEquals(date, calorieThresholdEntity.getThresholdDate());
    }

    @Test
    void testTotalCalories() {
        calorieThresholdEntity.setTotalCalories(2000);
        assertEquals(2000, calorieThresholdEntity.getTotalCalories());
    }

    @Test
    void testIsWarningTriggered() {
        calorieThresholdEntity.setWarningTriggered(true);
        assertTrue(calorieThresholdEntity.isWarningTriggered());

        calorieThresholdEntity.setWarningTriggered(false);
        assertFalse(calorieThresholdEntity.isWarningTriggered());
    }

    @Test
    void testCalorieLimit() {
        calorieThresholdEntity.setCalorieLimit(2500);
        assertEquals(2500, calorieThresholdEntity.getCalorieLimit());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        calorieThresholdEntity.setCreatedAt(now);
        assertEquals(now, calorieThresholdEntity.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        calorieThresholdEntity.setUpdatedAt(now);
        assertEquals(now, calorieThresholdEntity.getUpdatedAt());
    }
}

