package com.calorator.repository;

import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.entity.UserEntity;
import com.calorator.repository.impl.MonthlyExpenditureRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonthlyExpenditureRepositoryImplTest {

    @InjectMocks
    private MonthlyExpenditureRepositoryImpl repository;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<MonthlyExpenditureEntity> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewEntity() {
        MonthlyExpenditureEntity newEntity = new MonthlyExpenditureEntity();
        newEntity.setMonth(LocalDate.of(2025, 1, 23));
        newEntity.setTotalSpent(BigDecimal.valueOf(100));
        newEntity.setUser(new UserEntity());

        repository.save(newEntity);

        verify(em, times(1)).persist(newEntity);
        verify(em, never()).merge(newEntity);
    }

    @Test
    void testSave_ExistingEntity() {
        MonthlyExpenditureEntity existingEntity = new MonthlyExpenditureEntity();
        existingEntity.setId(1L);
        existingEntity.setMonth(LocalDate.of(2025, 1, 1));
        existingEntity.setTotalSpent(BigDecimal.valueOf(100));
        existingEntity.setUser(new UserEntity());

        repository.save(existingEntity);

        verify(em, times(1)).merge(existingEntity);
        verify(em, never()).persist(existingEntity);
    }

    @Test
    void testFindByUserIdAndMonth_Found() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 23);
        MonthlyExpenditureEntity expectedEntity = new MonthlyExpenditureEntity();
        expectedEntity.setId(1L);
        expectedEntity.setMonth(month);
        expectedEntity.setTotalSpent(BigDecimal.valueOf(100));
        expectedEntity.setUser(new UserEntity());

        when(em.createQuery(anyString(), eq(MonthlyExpenditureEntity.class))).thenReturn(query);
        when(query.setParameter("userId", userId)).thenReturn(query);
        when(query.setParameter("month", month)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedEntity);

        MonthlyExpenditureEntity result = repository.findByUserIdAndMonth(userId, month);

        assertNotNull(result);
        assertEquals(expectedEntity, result);

        verify(em, times(1)).createQuery(anyString(), eq(MonthlyExpenditureEntity.class));
        verify(query, times(1)).setParameter("userId", userId);
        verify(query, times(1)).setParameter("month", month);
        verify(query, times(1)).getSingleResult();
    }

    @Test
    void testFindByUserIdAndMonth_NotFound() {
        Long userId = 1L;
        LocalDate month = LocalDate.of(2025, 1, 1);

        when(em.createQuery(anyString(), eq(MonthlyExpenditureEntity.class))).thenReturn(query);
        when(query.setParameter("userId", userId)).thenReturn(query);
        when(query.setParameter("month", month)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new RuntimeException("No result"));

        MonthlyExpenditureEntity result = repository.findByUserIdAndMonth(userId, month);

        assertNull(result);

        verify(em, times(1)).createQuery(anyString(), eq(MonthlyExpenditureEntity.class));
        verify(query, times(1)).setParameter("userId", userId);
        verify(query, times(1)).setParameter("month", month);
        verify(query, times(1)).getSingleResult();
    }

    @Test
    void testCalculateMonthlySpending() {
        Long userId = 1L;
        int month = 1;
        int year = 2025;
        BigDecimal expectedTotal = BigDecimal.valueOf(500);

        Query nativeQuery = mock(Query.class);

        when(em.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("userId", userId)).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("month", month)).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("year", year)).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(expectedTotal);

        BigDecimal result = repository.calculateMonthlySpending(userId, month, year);

        assertNotNull(result);
        assertEquals(expectedTotal, result);

        verify(em, times(1)).createNativeQuery(anyString());
        verify(nativeQuery, times(1)).setParameter("userId", userId);
        verify(nativeQuery, times(1)).setParameter("month", month);
        verify(nativeQuery, times(1)).setParameter("year", year);
        verify(nativeQuery, times(1)).getSingleResult();
    }
    @Test
    void testCalculateMonthlySpending_NoExpenditure() {
        Long userId = 1L;
        int month = 1;
        int year = 2025;
        BigDecimal expectedTotal = BigDecimal.ZERO;

        Query nativeQuery = mock(Query.class);

        when(em.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("userId", userId)).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("month", month)).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("year", year)).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(expectedTotal);

        BigDecimal result = repository.calculateMonthlySpending(userId, month, year);

        assertNotNull(result);
        assertEquals(expectedTotal, result);

        verify(em, times(1)).createNativeQuery(anyString());
        verify(nativeQuery, times(1)).setParameter("userId", userId);
        verify(nativeQuery, times(1)).setParameter("month", month);
        verify(nativeQuery, times(1)).setParameter("year", year);
        verify(nativeQuery, times(1)).getSingleResult();
    }

    @Test
    void testCalculateMonthlySpending_Exception() {
        Long userId = 1L;
        int month = 1;
        int year = 2025;

        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter("userId", userId)).thenReturn(query);
        when(query.setParameter("month", month)).thenReturn(query);
        when(query.setParameter("year", year)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                repository.calculateMonthlySpending(userId, month, year));

        assertEquals("An error occurred while calculating monthly spending.", exception.getMessage());

        verify(em, times(1)).createNativeQuery(anyString());
        verify(query, times(1)).setParameter("userId", userId);
        verify(query, times(1)).setParameter("month", month);
        verify(query, times(1)).setParameter("year", year);
        verify(query, times(1)).getSingleResult();
    }
}
