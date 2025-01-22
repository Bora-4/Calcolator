package com.calorator.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
    }

    @Test
    void testId() {
        userEntity.setId(1L);
        assertEquals(1L, userEntity.getId());
    }

    @Test
    void testName() {
        userEntity.setName("John Doe");
        assertEquals("John Doe", userEntity.getName());
    }

    @Test
    void testEmail() {
        userEntity.setEmail("johndoe@example.com");
        assertEquals("johndoe@example.com", userEntity.getEmail());
    }

    @Test
    void testPassword() {
        userEntity.setPassword("securePassword123");
        assertEquals("securePassword123", userEntity.getPassword());
    }

    @Test
    void testRole() {
        userEntity.setRole(UserEntity.Role.admin);
        assertEquals(UserEntity.Role.admin, userEntity.getRole());

        userEntity.setRole(UserEntity.Role.user);
        assertEquals(UserEntity.Role.user, userEntity.getRole());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        userEntity.setCreatedAt(now);
        assertEquals(now, userEntity.getCreatedAt());
    }

    @Test
    void testUpdatedAt() {
        LocalDateTime now = LocalDateTime.now();
        userEntity.setUpdatedAt(now);
        assertEquals(now, userEntity.getUpdatedAt());
    }

    @Test
    void testOnCreate() {
        userEntity.onCreate();
        assertNotNull(userEntity.getCreatedAt());
        assertNotNull(userEntity.getUpdatedAt());
    }

    @Test
    void testOnUpdate() {
        userEntity.onUpdate();
        assertNotNull(userEntity.getUpdatedAt());
    }

    @Test
    void testToString() {
        userEntity.setId(1L);
        userEntity.setName("John Doe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setPassword("securePassword123");
        userEntity.setRole(UserEntity.Role.admin);
        LocalDateTime now = LocalDateTime.now();
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);

        String expected = "UserEntity{id=1, name='John Doe', email='johndoe@example.com', password='securePassword123', role=admin, createdAt=" + now + ", updatedAt=" + now + "}";
        assertEquals(expected, userEntity.toString());
    }
}

