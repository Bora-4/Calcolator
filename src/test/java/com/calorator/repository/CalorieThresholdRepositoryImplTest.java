package com.calorator.repository;

import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.repository.impl.CalorieThresholdRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalorieThresholdRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CalorieThresholdRepositoryImpl calorieThresholdRepository;

    @Mock
    private TypedQuery<CalorieThresholdEntity> typedQuery;

    @Mock
    private TypedQuery<Date> dateQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUserIdAndDate_Found() {
        Long userId = 1L;
        Date date = new Date();
        CalorieThresholdEntity expectedEntity = new CalorieThresholdEntity();

        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("date", date)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expectedEntity);

        CalorieThresholdEntity result = calorieThresholdRepository.findByUserIdAndDate(userId, date);

        assertEquals(expectedEntity, result);
        verify(typedQuery).getSingleResult();
    }

    @Test
    void testFindByUserIdAndDate_NotFound() {
        Long userId = 1L;
        Date date = new Date();

        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("userId", userId)).thenReturn(typedQuery);
        when(typedQuery.setParameter("date", date)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        CalorieThresholdEntity result = calorieThresholdRepository.findByUserIdAndDate(userId, date);

        assertNull(result);
    }

    @Test
    void testSave_NewEntity() {
        CalorieThresholdEntity newEntity = new CalorieThresholdEntity();
        newEntity.setThresholdId(0);

        calorieThresholdRepository.save(newEntity);

        verify(entityManager).persist(newEntity);
    }

    @Test
    void testSave_ExistingEntity() {
        CalorieThresholdEntity existingEntity = new CalorieThresholdEntity();
        existingEntity.setThresholdId(1);

        when(entityManager.merge(existingEntity)).thenReturn(existingEntity);

        CalorieThresholdEntity result = calorieThresholdRepository.save(existingEntity);

        assertEquals(existingEntity, result);
        verify(entityManager).merge(existingEntity);
    }

    @Test
    void testDeleteById_EntityExists() {
        Long id = 1L;
        CalorieThresholdEntity entity = new CalorieThresholdEntity();
        when(entityManager.find(CalorieThresholdEntity.class, id)).thenReturn(entity);

        calorieThresholdRepository.deleteById(id);

        verify(entityManager).remove(entity);
    }

    @Test
    void testDeleteById_EntityNotFound() {
        Long id = 1L;
        when(entityManager.find(CalorieThresholdEntity.class, id)).thenReturn(null);

        calorieThresholdRepository.deleteById(id);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindExceededThresholdDates() {
        Long userId = 1L;
        Date startDate = new Date();
        Date endDate = new Date();
        List<Date> expectedDates = List.of(startDate, endDate);

        when(entityManager.createQuery(anyString(), eq(Date.class))).thenReturn(dateQuery);
        when(dateQuery.setParameter("userId", userId)).thenReturn(dateQuery);
        when(dateQuery.setParameter("startDate", startDate)).thenReturn(dateQuery);
        when(dateQuery.setParameter("endDate", endDate)).thenReturn(dateQuery);
        when(dateQuery.getResultList()).thenReturn(expectedDates);

        List<Date> result = calorieThresholdRepository.findExceededThresholdDates(userId, startDate, endDate);

        assertEquals(expectedDates, result);
        verify(dateQuery).getResultList();
    }
}
