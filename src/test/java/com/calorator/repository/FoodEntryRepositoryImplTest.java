package com.calorator.repository;

import com.calorator.entity.FoodEntryEntity;
import com.calorator.repository.impl.FoodEntryRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodEntryRepositoryImplTest {

    @InjectMocks
    private FoodEntryRepositoryImpl repository;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<FoodEntryEntity> typedQuery;

    @Mock
    private TypedQuery<Long> countQuery;

    @Mock
    private TypedQuery<BigDecimal> bigDecimalQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        FoodEntryEntity foodEntry = new FoodEntryEntity();
        repository.save(foodEntry);
        verify(em, times(1)).persist(foodEntry);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        FoodEntryEntity foodEntry = new FoodEntryEntity();
        when(em.find(FoodEntryEntity.class, id)).thenReturn(foodEntry);

        FoodEntryEntity result = repository.findById(id);

        assertNotNull(result);
        assertEquals(foodEntry, result);
        verify(em, times(1)).find(FoodEntryEntity.class, id);
    }

    @Test
    void testFindFoodEntriesLast7Days() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        List<FoodEntryEntity> foodEntries = Arrays.asList(new FoodEntryEntity(), new FoodEntryEntity());

        when(em.createQuery(anyString(), eq(FoodEntryEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("sevenDaysAgo", sevenDaysAgo)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(foodEntries);

        List<FoodEntryEntity> result = repository.findFoodEntriesLast7Days(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testCountFoodEntriesLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
        when(countQuery.setParameter("sevenDaysAgo", sevenDaysAgo)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(5L);

        Long count = repository.countFoodEntriesLast7Days();

        assertEquals(5L, count);
        verify(countQuery, times(1)).getSingleResult();
    }

    @Test
    void testUpdate() {
        FoodEntryEntity foodEntry = new FoodEntryEntity();
        repository.update(foodEntry);
        verify(em, times(1)).merge(foodEntry);
    }

    @Test
    void testFindAll() {
        List<FoodEntryEntity> foodEntries = Arrays.asList(new FoodEntryEntity(), new FoodEntryEntity());
        when(em.createQuery(anyString(), eq(FoodEntryEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(foodEntries);

        List<FoodEntryEntity> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testDelete() {
        Long id = 1L;
        FoodEntryEntity foodEntry = new FoodEntryEntity();
        when(em.find(FoodEntryEntity.class, id)).thenReturn(foodEntry);

        repository.delete(id);

        verify(em, times(1)).remove(foodEntry);
    }

    @Test
    void testCalculateMonthlySpending() {
        Long userId = 1L;
        int month = 1;
        int year = 2023;
        BigDecimal spending = new BigDecimal("100.50");

        when(em.createNativeQuery(anyString())).thenReturn(bigDecimalQuery);
        when(bigDecimalQuery.setParameter("userId", userId)).thenReturn(bigDecimalQuery);
        when(bigDecimalQuery.setParameter("month", month)).thenReturn(bigDecimalQuery);
        when(bigDecimalQuery.setParameter("year", year)).thenReturn(bigDecimalQuery);
        when(bigDecimalQuery.getSingleResult()).thenReturn(spending);

        BigDecimal result = repository.calculateMonthlySpending(userId, month, year);

        assertNotNull(result);
        assertEquals(spending, result);
        verify(bigDecimalQuery, times(1)).getSingleResult();
    }

    @Test
    void testEntryDateFiltering() {
        Long userId = 1L;
        LocalDateTime startDate = LocalDateTime.now().minusDays(10);
        LocalDateTime endDate = LocalDateTime.now();
        List<FoodEntryEntity> foodEntries = Arrays.asList(new FoodEntryEntity(), new FoodEntryEntity());

        when(em.createQuery(anyString(), eq(FoodEntryEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("startDate", startDate)).thenReturn(typedQuery);
        when(typedQuery.setParameter("endDate", endDate)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(foodEntries);

        List<FoodEntryEntity> result = repository.entryDateFiltering(userId, startDate, endDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }
}
