package com.calorator.repository;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.repository.impl.MonthlyExpenditureRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonthlyExpenditureRepositoryImplTest {

    @InjectMocks
    private MonthlyExpenditureRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<MonthlyExpenditureEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewEntity() {
        MonthlyExpenditureEntity entity = new MonthlyExpenditureEntity();
        entity.setId(null); // Ensure it is treated as a new entity

        repository.save(entity);

        verify(entityManager, times(1)).persist(entity);
        verify(entityManager, never()).merge(entity);
    }

    @Test
    void testSave_ExistingEntity() {
        MonthlyExpenditureEntity entity = new MonthlyExpenditureEntity();
        entity.setId(1L); // Simulate an existing entity

        repository.save(entity);

        verify(entityManager, times(1)).merge(entity);
        verify(entityManager, never()).persist(entity);
    }

    @Test
    void testSave_NullEntity() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }

    @Test
    void testFindByUserIdAndMonth_Found() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 1);
        MonthlyExpenditureEntity entity = new MonthlyExpenditureEntity();

        when(entityManager.createQuery(anyString(), eq(MonthlyExpenditureEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("month", month)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(entity);

        MonthlyExpenditureEntity result = repository.findByUserIdAndMonth(userId, month);

        assertNotNull(result);
        assertEquals(entity, result);
        verify(typedQuery, times(1)).getSingleResult();
    }

    @Test
    void testFindByUserIdAndMonth_NotFound() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 1);

        when(entityManager.createQuery(anyString(), eq(MonthlyExpenditureEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("month", month)).thenReturn(typedQuery);

        MonthlyExpenditureEntity result = repository.findByUserIdAndMonth(userId, month);

        assertNull(result);
        verify(typedQuery, times(1)).getSingleResult();
    }

    @Test
    void testFindByUserIdAndMonth_NullParameters() {
        assertThrows(NullPointerException.class, () -> repository.findByUserIdAndMonth(null, LocalDate.now()));
        assertThrows(NullPointerException.class, () -> repository.findByUserIdAndMonth(1L, null));
    }
}
