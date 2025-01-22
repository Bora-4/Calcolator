package com.calorator.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReportEntityTest {

    private ReportEntity reportEntity;

    @BeforeEach
    void setUp() {
        reportEntity = new ReportEntity();
    }

    @Test
    void testId() {
        reportEntity.setId(1L);
        assertEquals(1L, reportEntity.getId());
    }

    @Test
    void testAdmin() {
        UserEntity admin = new UserEntity();
        reportEntity.setAdmin(admin);
        assertEquals(admin, reportEntity.getAdmin());
    }

    @Test
    void testReportDate() {
        LocalDate reportDate = LocalDate.of(2025, 1, 22);
        reportEntity.setReportDate(reportDate);
        assertEquals(reportDate, reportEntity.getReportDate());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        reportEntity.setCreatedAt(now);
        assertEquals(now, reportEntity.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        reportEntity.setUpdatedAt(now);
        assertEquals(now, reportEntity.getUpdatedAt());
    }

    @Test
    void testOnCreate() {
        reportEntity.onCreate();
        assertNotNull(reportEntity.getCreatedAt());
        assertNotNull(reportEntity.getUpdatedAt());
    }

    @Test
    void testOnUpdate() {
        reportEntity.onUpdate();
        assertNotNull(reportEntity.getUpdatedAt());
    }
}
