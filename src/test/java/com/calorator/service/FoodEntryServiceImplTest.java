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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodEntryServiceImplTest {

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
    void testSave_ValidFoodEntry() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);  // Make sure you have a proper constructor or use setId()
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(1L)).thenReturn(userEntity);
        doNothing().when(foodEntryRepository).save(any(FoodEntryEntity.class));  // Mocking void method

        foodEntryService.save(foodEntryDTO);

        verify(userRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).save(any(FoodEntryEntity.class));
    }

    @Test
    void testSave_UserNotFound() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);

        when(userRepository.findById(1L)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        assertEquals("User with id 1 was not found.", exception.getMessage());
    }

    @Test
    void testSave_InvalidUserId() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        assertEquals("User information is required.", exception.getMessage());
    }

    @Test
    void testFindById_FoodEntryFound() {
        Long id = 1L;
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();
        when(foodEntryRepository.findById(id)).thenReturn(foodEntryEntity);

        FoodEntryDTO result = foodEntryService.findById(id);

        assertNotNull(result);
        verify(foodEntryRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_FoodEntryNotFound() {
        Long id = 1L;
        when(foodEntryRepository.findById(id)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.findById(id);
        });

        assertEquals("Food entry with id 1 was not found.", exception.getMessage());
    }

    @Test
    void testFindFoodEntriesLast7Days() {
        long userId = 1L;
        List<FoodEntryEntity> foodEntries = List.of(new FoodEntryEntity());
        when(foodEntryRepository.findFoodEntriesLast7Days(userId)).thenReturn(foodEntries);

        List<FoodEntryDTO> result = foodEntryService.findFoodEntriesLast7Days(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindFoodEntriesLast7Days_InvalidUserId() {
        long userId = -1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.findFoodEntriesLast7Days(userId);
        });

        assertEquals("userId must be a positive value.", exception.getMessage());
    }

    @Test
    void testUpdate_ValidFoodEntry() {
        // Setting up the DTO
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);

        // Setting up entities
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        FoodEntryEntity existingFoodEntry = new FoodEntryEntity();
        existingFoodEntry.setId(1L);
        existingFoodEntry.setCreatedAt(LocalDateTime.now().minusDays(5));

        FoodEntryEntity updatedFoodEntry = new FoodEntryEntity();
        updatedFoodEntry.setId(1L);
        updatedFoodEntry.setCreatedAt(existingFoodEntry.getCreatedAt());

        // Mocking repository methods
        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(foodEntryRepository.findById(1L)).thenReturn(existingFoodEntry);
        doNothing().when(foodEntryRepository).update(any(FoodEntryEntity.class));

        // Perform the update operation
        foodEntryService.update(foodEntryDTO);

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).update(any(FoodEntryEntity.class));
    }



    @Test
    void testUpdate_FoodEntryNotFound() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Valid Food"); // Add a valid food name to avoid IllegalArgumentException
        foodEntryDTO.setCalories(100); // Add valid calories
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1)); // Add a valid entry date (in the past)

        // Mocking food entry retrieval to return null (simulate food entry not found)
        when(foodEntryRepository.findById(1L)).thenReturn(null);

        // Test that the correct exception is thrown when the food entry is not found
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.update(foodEntryDTO);
        });

        // Verify that the exception message matches the expected one
        assertEquals("Food entry with id 1 was not found.", exception.getMessage());

        // Verify that the repository methods are called as expected
        verify(foodEntryRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(foodEntryRepository);  // Verify no further interactions with the repository
    }


    @Test
    void testUpdate_UserNotFound() {
        // Setting up a valid FoodEntryDTO
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);

        foodEntryDTO.setFoodName("Valid Food Name"); // Avoid IllegalArgumentException
        foodEntryDTO.setCalories(100); // Avoid IllegalArgumentException
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1)); // Avoid IllegalArgumentException

        // Mocking repository behavior
        when(foodEntryRepository.findById(1L)).thenReturn(new FoodEntryEntity());
        when(userRepository.findById(1L)).thenReturn(null);

        // Expecting EntityNotFoundException
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.update(foodEntryDTO);
        });

        // Verifying exception message
        assertEquals("User with id 1 was not found.", exception.getMessage());

        // Verifying repository interactions
        verify(foodEntryRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(foodEntryRepository, userRepository);
    }



    @Test
    void testDelete_FoodEntryFound() {
        Long id = 1L;
        FoodEntryEntity foodEntryEntity = new FoodEntryEntity();

        when(foodEntryRepository.findById(id)).thenReturn(foodEntryEntity);

        foodEntryService.delete(id);

        verify(foodEntryRepository, times(1)).delete(id);
    }

    @Test
    void testDelete_FoodEntryNotFound() {
        Long id = 1L;
        when(foodEntryRepository.findById(id)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.delete(id);
        });

        assertEquals("Food entry with id 1 was not found.", exception.getMessage());
    }
}
