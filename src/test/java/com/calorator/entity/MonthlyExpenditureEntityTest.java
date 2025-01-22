package com.calorator.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyExpenditureEntityTest {

    private MonthlyExpenditureEntity monthlyExpenditureEntity;

    @BeforeEach
    void setUp() {
        monthlyExpenditureEntity = new MonthlyExpenditureEntity();
    }

    @Test
    void testId() {
        monthlyExpenditureEntity.setId(1L);
        assertEquals(1L, monthlyExpenditureEntity.getId());
    }

    @Test
    void testUser() {
        UserEntity user = new UserEntity();
        monthlyExpenditureEntity.setUser(user);
        assertEquals(user, monthlyExpenditureEntity.getUser());
    }

    @Test
    void testMonth() {
        LocalDate month = LocalDate.of(2025, 1, 1);
        monthlyExpenditureEntity.setMonth(month);
        assertEquals(month, monthlyExpenditureEntity.getMonth());
    }

    @Test
    void testTotalSpent() {
        BigDecimal totalSpent = new BigDecimal("1234.56");
        monthlyExpenditureEntity.setTotalSpent(totalSpent);
        assertEquals(totalSpent, monthlyExpenditureEntity.getTotalSpent());
    }

    @Test
    void testWarning() {
        monthlyExpenditureEntity.setWarning(true);
        assertTrue(monthlyExpenditureEntity.isWarning());

        monthlyExpenditureEntity.setWarning(false);
        assertFalse(monthlyExpenditureEntity.isWarning());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        monthlyExpenditureEntity.setCreatedAt(now);
        assertEquals(now, monthlyExpenditureEntity.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        monthlyExpenditureEntity.setUpdatedAt(now);
        assertEquals(now, monthlyExpenditureEntity.getUpdatedAt());
    }

    @Test
    void testOnCreate() {
        monthlyExpenditureEntity.onCreate();
        assertNotNull(monthlyExpenditureEntity.getCreatedAt());
        assertNotNull(monthlyExpenditureEntity.getUpdatedAt());
    }

    @Test
    void testOnUpdate() {
        monthlyExpenditureEntity.onUpdate();
        assertNotNull(monthlyExpenditureEntity.getUpdatedAt());
    }
}

