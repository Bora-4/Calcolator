package com.calorator.service;

import com.calorator.dto.FoodEntryDTO;
import com.calorator.dto.UserDTO;
import com.calorator.entity.FoodEntryEntity;
import com.calorator.entity.UserEntity;
import com.calorator.repository.FoodEntryRepository;
import com.calorator.repository.UserRepository;
import com.calorator.service.impl.FoodEntryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodEntryServiceImplTest {

    @InjectMocks
    private FoodEntryServiceImpl foodEntryService;

    @Mock
    private FoodEntryRepository foodEntryRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_validFoodEntry_shouldSaveSuccessfully() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setPrice(1.50);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1));

        UserEntity user = new UserEntity();
        user.setId(1L);

        foodEntryDTO.setUser(new UserDTO());
        foodEntryDTO.getUser().setId(1L);

        when(userRepository.findById(1L)).thenReturn(user);
        doNothing().when(foodEntryRepository).save(any(FoodEntryEntity.class));

        assertDoesNotThrow(() -> foodEntryService.save(foodEntryDTO));
        verify(foodEntryRepository, times(1)).save(any(FoodEntryEntity.class));
    }


    @Test
    void save_invalidUser_shouldThrowException() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setUser(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> foodEntryService.save(foodEntryDTO));
        assertEquals("User information is required.", exception.getMessage());
    }

    @Test
    void findById_existingId_shouldReturnFoodEntry() {
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        foodEntryEntity.setId(1L);
        foodEntryEntity.setFoodName("Apple");

        when(foodEntryRepository.findById(1L)).thenReturn(foodEntryEntity);

        FoodEntryDTO result = foodEntryService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Apple", result.getFoodName());
    }

    @Test
    void findById_nonExistingId_shouldThrowException() {
        when(foodEntryRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> foodEntryService.findById(1L));
        assertEquals("Food entry with id 1 was not found.", exception.getMessage());
    }

    @Test
    void findFoodEntriesLast7Days_shouldReturnEntries() {
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        foodEntryEntity.setId(1L);

        when(foodEntryRepository.findFoodEntriesLast7Days(1L)).thenReturn(Collections.singletonList(foodEntryEntity));

        List<FoodEntryDTO> result = foodEntryService.findFoodEntriesLast7Days(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void countFoodEntriesLast7Days_shouldReturnCount() {
        when(foodEntryRepository.countFoodEntriesLast7Days()).thenReturn(5L);

        Long count = foodEntryService.countFoodEntriesLast7Days();

        assertEquals(5L, count);
    }

    @Test
    void update_existingEntry_shouldUpdateSuccessfully() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Updated Apple");
        foodEntryDTO.setCalories(120);
        foodEntryDTO.setPrice(2.00);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(2));
        foodEntryDTO.setUser(new UserDTO());
        foodEntryDTO.getUser().setId(1L);

        FoodEntryEntity existingEntry = new FoodEntryEntity();
        existingEntry.setId(1L);
        existingEntry.setFoodName("Old Apple");
        existingEntry.setCalories(100);
        existingEntry.setPrice(1.50);
        existingEntry.setEntryDate(LocalDateTime.now().minusDays(3));
        existingEntry.setCreatedAt(LocalDateTime.now().minusDays(10));

        UserEntity user = new UserEntity();
        user.setId(1L);

        when(foodEntryRepository.findById(1L)).thenReturn(existingEntry);
        when(userRepository.findById(1L)).thenReturn(user);

        doAnswer(invocation -> {
            FoodEntryEntity updatedEntry = invocation.getArgument(0);
            assertEquals("Updated Apple", updatedEntry.getFoodName());
            assertEquals(120, updatedEntry.getCalories());
            assertEquals(BigDecimal.valueOf(2.00), updatedEntry.getPrice());
            return null;
        }).when(foodEntryRepository).update(any(FoodEntryEntity.class));

    }


    @Test
    void update_nonExistingEntry_shouldThrowException() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);

        when(foodEntryRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> foodEntryService.update(foodEntryDTO));
        assertEquals("Food entry with id 1 was not found.", exception.getMessage());
    }

    @Test
    void delete_existingEntry_shouldDeleteSuccessfully() {
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        foodEntryEntity.setId(1L);

        when(foodEntryRepository.findById(1L)).thenReturn(foodEntryEntity);
        doNothing().when(foodEntryRepository).delete(1L);

        assertDoesNotThrow(() -> foodEntryService.delete(1L));
        verify(foodEntryRepository, times(1)).delete(1L);
    }

    @Test
    void delete_nonExistingEntry_shouldThrowException() {
        when(foodEntryRepository.findById(1L)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> foodEntryService.delete(1L));
        assertEquals("Food entry with id 1 was not found.", exception.getMessage());
    }

    @Test
    void calculateMonthlySpending_shouldReturnSpending() {
        when(foodEntryRepository.calculateMonthlySpending(1L, 1, 2023)).thenReturn(BigDecimal.valueOf(100));

        BigDecimal spending = foodEntryService.calculateMonthlySpending(1L, 1, 2023);

        assertEquals(BigDecimal.valueOf(100), spending);
    }

    @Test
    void entryDateFiltering_validInputs_shouldReturnEntries() {
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        foodEntryEntity.setId(1L);

        when(foodEntryRepository.entryDateFiltering(1L, LocalDateTime.now().minusDays(7), LocalDateTime.now()))
                .thenReturn(Collections.singletonList(foodEntryEntity));

        List<FoodEntryDTO> result = foodEntryService.entryDateFiltering(1L, LocalDateTime.now().minusDays(7), LocalDateTime.now());

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
