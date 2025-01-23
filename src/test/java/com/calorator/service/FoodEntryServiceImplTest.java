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
        foodEntryDTO.setUser(userDTO);  // Ensure user is properly set
        foodEntryDTO.setFoodName("Apple");  // Set a valid food name
        foodEntryDTO.setCalories(100);  // Set a valid calorie value

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        // Mocking the user lookup
        when(userRepository.findById(1L)).thenReturn(userEntity);

        // Mocking the save operation for the food entry
        doNothing().when(foodEntryRepository).save(any(FoodEntryEntity.class));

        // Call the save method
        foodEntryService.save(foodEntryDTO);

        // Verify that the methods are called
        verify(userRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).save(any(FoodEntryEntity.class));
    }


    @Test
    void testSave_UserNotFound() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);
        foodEntryDTO.setFoodName("Apple");  // Ensure food name is set
        foodEntryDTO.setCalories(100);  // Set valid calories to avoid NullPointerException

        // Mocking that the user is not found (null returned from the repository)
        when(userRepository.findById(1L)).thenReturn(null);

        // Expecting EntityNotFoundException to be thrown
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        // Checking that the correct exception message is thrown
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

    @Test
    void testSave_InvalidFoodName() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);
        foodEntryDTO.setFoodName("");  // Empty food name

        when(userRepository.findById(1L)).thenReturn(new UserEntity());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        assertEquals("Food name must not be empty.", exception.getMessage());
    }


    @Test
    void testUpdate_InvalidEntryDate() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setEntryDate(LocalDateTime.now().plusDays(1)); // Future date

        when(foodEntryRepository.findById(1L)).thenReturn(new FoodEntryEntity());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.update(foodEntryDTO);
        });

        assertEquals("Entry date cannot be in the future.", exception.getMessage());
    }

    @Test
    void testSave_InvalidCalories() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(-100);  // Invalid calories

        when(userRepository.findById(1L)).thenReturn(new UserEntity());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        assertEquals("Calories must be greater than 0.", exception.getMessage());
    }


    @Test
    void testValidateFoodEntry_NullDTO() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.validateFoodEntry(null);
        });

        assertEquals("FoodEntryDTO must not be null.", exception.getMessage());
    }

    @Test
    void testEntryDateFiltering_InvalidUserId() {
        Long userId = -1L; // Invalid userId
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.entryDateFiltering(userId, startDate, endDate);
        });

        assertEquals("userId must be a positive value.", exception.getMessage());
    }



    @Test
    void testCountFoodEntriesLast7Days_Empty() {
        when(foodEntryRepository.countFoodEntriesLast7Days()).thenReturn(0L);

        Long result = foodEntryService.countFoodEntriesLast7Days();

        assertEquals(0L, result);
    }



}
