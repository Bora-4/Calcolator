package com.calorator.repository;


import com.calorator.entity.UserSpendingEntity;
import com.calorator.repository.UserSpendingRepository;
import com.calorator.repository.impl.UserSpendingRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSpendingRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserSpendingRepositoryImpl userSpendingRepository;

    private UserSpendingEntity userSpendingEntity;

    @BeforeEach
    void setUp() {
        userSpendingEntity = new UserSpendingEntity();
        userSpendingEntity.setId(1L);
        userSpendingEntity.setTotalSpent(BigDecimal.valueOf(100));
    }

    @Test
    void save() {
        // Given
        doNothing().when(entityManager).persist(any(UserSpendingEntity.class));

        // When
        userSpendingRepository.save(userSpendingEntity);

        // Then
        verify(entityManager, times(1)).persist(userSpendingEntity);
    }

    @Test
    void update() {
        // Given
        doNothing().when(entityManager).merge(any(UserSpendingEntity.class));

        // When
        userSpendingRepository.update(userSpendingEntity);

        // Then
        verify(entityManager, times(1)).merge(userSpendingEntity);
    }

    @Test
    void delete() {
        // Given
        when(entityManager.find(UserSpendingEntity.class, 1L)).thenReturn(userSpendingEntity);
        doNothing().when(entityManager).remove(any(UserSpendingEntity.class));

        // When
        userSpendingRepository.delete(1L);

        // Then
        verify(entityManager, times(1)).remove(userSpendingEntity);
    }

    @Test
    void delete_NotFound() {
        // Given
        when(entityManager.find(UserSpendingEntity.class, 1L)).thenReturn(null);

        // When
        userSpendingRepository.delete(1L);

        // Then
        verify(entityManager, times(0)).remove(any(UserSpendingEntity.class));
    }

    @Test
    void findById() {
        // Given
        when(entityManager.find(UserSpendingEntity.class, 1L)).thenReturn(userSpendingEntity);

        // When
        UserSpendingEntity foundEntity = userSpendingRepository.findById(1L);

        // Then
        assertNotNull(foundEntity);
        assertEquals(userSpendingEntity.getId(), foundEntity.getId());
        verify(entityManager, times(1)).find(UserSpendingEntity.class, 1L);
    }

    @Test
    void findAll() {
        // Given
        List<UserSpendingEntity> spendingList = List.of(userSpendingEntity);
        when(entityManager.createQuery("SELECT u FROM UserSpendingEntity u", UserSpendingEntity.class).getResultList())
                .thenReturn(spendingList);

        // When
        List<UserSpendingEntity> foundSpendingList = userSpendingRepository.findAll();

        // Then
        assertNotNull(foundSpendingList);
        assertEquals(1, foundSpendingList.size());
        verify(entityManager, times(1)).createQuery("SELECT u FROM UserSpendingEntity u", UserSpendingEntity.class);
    }
}
