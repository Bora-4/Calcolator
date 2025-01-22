package com.calorator.service;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.dto.UserDTO;
import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.CalorieThresholdMapper;
import com.calorator.repository.CalorieThresholdRepository;
import com.calorator.repository.UserRepository;
import com.calorator.service.impl.CalorieThresholdServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalorieThresholdServiceImplTest {

    @InjectMocks
    private CalorieThresholdServiceImpl calorieThresholdService;

    @Mock
    private CalorieThresholdRepository calorieThresholdRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetThresholdByUserIdAndDate() {
        Long userId = 1L;
        Date date = new Date();
        CalorieThresholdEntity entity = new CalorieThresholdEntity();
        CalorieThresholdDTO dto = new CalorieThresholdDTO();

        when(calorieThresholdRepository.findByUserIdAndDate(userId, date)).thenReturn(entity);

        try (var mocked = mockStatic(CalorieThresholdMapper.class)) {
            mocked.when(() -> CalorieThresholdMapper.toDTO(entity)).thenReturn(dto);

            CalorieThresholdDTO result = calorieThresholdService.getThresholdByUserIdAndDate(userId, date);

            assertNotNull(result);
            verify(calorieThresholdRepository, times(1)).findByUserIdAndDate(userId, date);
            mocked.verify(() -> CalorieThresholdMapper.toDTO(entity), times(1)); // Ensure that toDTO is called
        }
    }


    @Test
    void testSaveThreshold() {
        CalorieThresholdDTO dto = new CalorieThresholdDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        dto.setUser(userDTO);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        CalorieThresholdEntity entity = new CalorieThresholdEntity();
        CalorieThresholdEntity savedEntity = new CalorieThresholdEntity();
        CalorieThresholdDTO savedDto = new CalorieThresholdDTO();

        when(userRepository.findById(1L)).thenReturn(userEntity);
        when(calorieThresholdRepository.save(entity)).thenReturn(savedEntity);

        try (var mocked = mockStatic(CalorieThresholdMapper.class)) {
            mocked.when(() -> CalorieThresholdMapper.toEntity(dto, userEntity)).thenReturn(entity);
            mocked.when(() -> CalorieThresholdMapper.toDTO(savedEntity)).thenReturn(savedDto);


            CalorieThresholdDTO result = calorieThresholdService.saveThreshold(dto);

            assertNotNull(result);
            assertEquals(savedDto, result);
            verify(userRepository, times(1)).findById(1L);
            verify(calorieThresholdRepository, times(1)).save(entity);
            mocked.verify(() -> CalorieThresholdMapper.toEntity(dto, userEntity), times(1));
            mocked.verify(() -> CalorieThresholdMapper.toDTO(savedEntity), times(1));
        }
    }


    @Test
    void testUpdateTotalCalories_UserNotFound() {
        Long userId = 1L;
        int calories = 500;
        Date date = new Date();

        when(userRepository.findById(userId)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> calorieThresholdService.updateTotalCalories(userId, calories, date)
        );

        assertEquals("User with id 1 was not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(calorieThresholdRepository);
    }

    @Test
    void testUpdateTotalCalories_ExistingThreshold() {
        Long userId = 1L;
        int calories = 500;
        Date date = new Date();

        UserEntity user = new UserEntity();
        user.setId(userId);

        CalorieThresholdEntity existingThreshold = new CalorieThresholdEntity();
        existingThreshold.setTotalCalories(1000);
        existingThreshold.setThresholdDate(date);

        when(userRepository.findById(userId)).thenReturn(user);
        when(calorieThresholdRepository.findByUserIdAndDate(userId, date)).thenReturn(existingThreshold);
        when(calorieThresholdRepository.save(existingThreshold)).thenReturn(existingThreshold);

        calorieThresholdService.updateTotalCalories(userId, calories, date);

        assertEquals(1500, existingThreshold.getTotalCalories());
        verify(userRepository, times(1)).findById(userId);
        verify(calorieThresholdRepository, times(1)).findByUserIdAndDate(userId, date);
        verify(calorieThresholdRepository, times(1)).save(existingThreshold);
    }

    @Test
    void testUpdateTotalCalories_NewThreshold() {
        Long userId = 1L;
        int calories = 500;
        Date date = new Date();

        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(user);
        when(calorieThresholdRepository.findByUserIdAndDate(userId, date)).thenReturn(null); // No existing threshold

        calorieThresholdService.updateTotalCalories(userId, calories, date);

        verify(calorieThresholdRepository, times(1)).save(any(CalorieThresholdEntity.class));
    }

    @Test
    void testIsThresholdExceeded() {
        Long userId = 1L;
        Date date = new Date();

        CalorieThresholdEntity entity = new CalorieThresholdEntity();
        entity.setTotalCalories(3000);
        entity.setWarningTriggered(false);

        when(calorieThresholdRepository.findByUserIdAndDate(userId, date)).thenReturn(entity);
        when(calorieThresholdRepository.save(entity)).thenReturn(entity);

        boolean result = calorieThresholdService.isThresholdExceeded(userId, date);

        assertTrue(result);
        assertTrue(entity.isWarningTriggered());
        verify(calorieThresholdRepository, times(1)).findByUserIdAndDate(userId, date);
        verify(calorieThresholdRepository, times(1)).save(entity);
    }

    @Test
    void testGetExceededThresholdDates() {
        Long userId = 1L;
        Date startDate = new Date();
        Date endDate = new Date();
        List<Date> exceededDates = List.of(startDate, endDate);

        when(calorieThresholdRepository.findExceededThresholdDates(userId, startDate, endDate))
                .thenReturn(exceededDates);

        List<Date> result = calorieThresholdService.getExceededThresholdDates(userId, startDate, endDate);

        assertEquals(exceededDates, result);
        verify(calorieThresholdRepository, times(1)).findExceededThresholdDates(userId, startDate, endDate);
    }
}
