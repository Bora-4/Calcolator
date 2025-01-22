package com.calorator.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserSpendingEntityTest {

    private UserSpendingEntity userSpendingEntity;

    @BeforeEach
    void setUp() {
        userSpendingEntity = new UserSpendingEntity();
    }

    @Test
    void testId() {
        userSpendingEntity.setId(1L);
        assertEquals(1L, userSpendingEntity.getId());
    }

    @Test
    void testUser() {
        UserEntity user = new UserEntity();
        userSpendingEntity.setUser(user);
        assertEquals(user, userSpendingEntity.getUser());
    }

    @Test
    void testReport() {
        ReportEntity report = new ReportEntity();
        userSpendingEntity.setReport(report);
        assertEquals(report, userSpendingEntity.getReport());
    }

    @Test
    void testTotalSpent() {
        BigDecimal totalSpent = new BigDecimal("123.45");
        userSpendingEntity.setTotalSpent(totalSpent);
        assertEquals(totalSpent, userSpendingEntity.getTotalSpent());
    }
}

