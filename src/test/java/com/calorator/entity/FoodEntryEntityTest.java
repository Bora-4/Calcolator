package com.calorator.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FoodEntryEntityTest {

    private FoodEntryEntity foodEntryEntity;

    @BeforeEach
    void setUp() {
        foodEntryEntity = new FoodEntryEntity();
    }

    @Test
    void testId() {
        foodEntryEntity.setId(1L);
        assertEquals(1L, foodEntryEntity.getId());
    }

    @Test
    void testUser() {
        UserEntity user = new UserEntity();
        foodEntryEntity.setUser(user);
        assertEquals(user, foodEntryEntity.getUser());
    }

    @Test
    void testFoodName() {
        foodEntryEntity.setFoodName("Apple");
        assertEquals("Apple", foodEntryEntity.getFoodName());
    }

    @Test
    void testCalories() {
        foodEntryEntity.setCalories(95);
        assertEquals(95, foodEntryEntity.getCalories());
    }

    @Test
    void testPrice() {
        foodEntryEntity.setPrice(1.99);
        assertEquals(1.99, foodEntryEntity.getPrice());
    }

    @Test
    void testEntryDate() {
        LocalDateTime now = LocalDateTime.now();
        foodEntryEntity.setEntryDate(now);
        assertEquals(now, foodEntryEntity.getEntryDate());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        foodEntryEntity.setCreatedAt(now);
        assertEquals(now, foodEntryEntity.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        foodEntryEntity.setUpdatedAt(now);
        assertEquals(now, foodEntryEntity.getUpdatedAt());
    }

    @Test
    void testOnCreate() {
        foodEntryEntity.onCreate();
        assertNotNull(foodEntryEntity.getCreatedAt());
        assertNotNull(foodEntryEntity.getUpdatedAt());
    }

    @Test
    void testOnUpdate() {
        foodEntryEntity.onUpdate();
        assertNotNull(foodEntryEntity.getUpdatedAt());
    }
}

