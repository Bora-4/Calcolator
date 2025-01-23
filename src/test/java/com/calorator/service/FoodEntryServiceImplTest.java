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
        foodEntryDTO.setUser(userDTO);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(userRepository.findById(1L)).thenReturn(userEntity);
        doNothing().when(foodEntryRepository).save(any(FoodEntryEntity.class));
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
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);

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
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        FoodEntryEntity existingFoodEntry = new FoodEntryEntity();
        existingFoodEntry.setId(1L);
        existingFoodEntry.setCreatedAt(LocalDateTime.now().minusDays(5));

        FoodEntryEntity updatedFoodEntry = new FoodEntryEntity();
        updatedFoodEntry.setId(1L);
        updatedFoodEntry.setCreatedAt(existingFoodEntry.getCreatedAt());

        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(foodEntryRepository.findById(1L)).thenReturn(existingFoodEntry);
        doNothing().when(foodEntryRepository).update(any(FoodEntryEntity.class));

        foodEntryService.update(foodEntryDTO);

        verify(userRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).findById(1L);
        verify(foodEntryRepository, times(1)).update(any(FoodEntryEntity.class));
    }

    @Test
    void testUpdate_FoodEntryNotFound() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);
        foodEntryDTO.setFoodName("Valid Food");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1));

        when(foodEntryRepository.findById(1L)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.update(foodEntryDTO);
        });

        assertEquals("Food entry with id 1 was not found.", exception.getMessage());

        verify(foodEntryRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(foodEntryRepository);  // Verify no further interactions with the repository
    }

    @Test
    void testUpdate_UserNotFound() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        foodEntryDTO.setId(1L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);

        foodEntryDTO.setFoodName("Valid Food Name");
        foodEntryDTO.setCalories(100);
        foodEntryDTO.setEntryDate(LocalDateTime.now().minusDays(1));

        when(foodEntryRepository.findById(1L)).thenReturn(new FoodEntryEntity());
        when(userRepository.findById(1L)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            foodEntryService.update(foodEntryDTO);
        });

        assertEquals("User with id 1 was not found.", exception.getMessage());

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
    void testValidateFoodEntry_NullDTO() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            foodEntryService.validateFoodEntry(null);
        });

        assertEquals("FoodEntryDTO must not be null.", exception.getMessage());
    }


    @Test
    void testCountFoodEntriesLast7Days_Empty() {
        when(foodEntryRepository.countFoodEntriesLast7Days()).thenReturn(0L);

        Long result = (long) foodEntryService.countFoodEntriesLast7Days();

        assertEquals(0L, result);
    }



    @Test
    void testSave_UserRepositoryFailure() {
        FoodEntryDTO foodEntryDTO = new FoodEntryDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        foodEntryDTO.setUser(userDTO);
        foodEntryDTO.setFoodName("Apple");
        foodEntryDTO.setCalories(100);

        when(userRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foodEntryService.save(foodEntryDTO);
        });

        assertEquals("Database error", exception.getMessage());
    }

}
