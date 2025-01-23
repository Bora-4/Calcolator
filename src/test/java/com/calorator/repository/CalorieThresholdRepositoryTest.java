package com.calorator.repository;


import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.repository.impl.CalorieThresholdRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalorieThresholdRepositoryTest {

    @InjectMocks
    private CalorieThresholdRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    private CalorieThresholdEntity mockEntity;
    private Date mockDate;

    @BeforeEach
    void setup() {
        mockEntity = new CalorieThresholdEntity();
        mockEntity.setThresholdId(1);
        mockEntity.setThresholdDate(new Date());
        mockEntity.setWarningTriggered(true);

        mockDate = new Date();
    }

    @Test
    void testFindByUserIdAndDate_Found() {
//        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class)))
//                .thenReturn(Mockito.mock(javax.persistence.TypedQuery.class));
        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class))
                .setParameter(eq("userId"), anyLong())
                .setParameter(eq("date"), any(Date.class))
                .getSingleResult()).thenReturn(mockEntity);

        CalorieThresholdEntity result = repository.findByUserIdAndDate(1L, mockDate);
        assertNotNull(result);
        assertEquals(mockEntity, result);
    }

    @Test
    void testFindByUserIdAndDate_NotFound() {
//        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class)))
//                .thenReturn(Mockito.mock(javax.persistence.TypedQuery.class));
        when(entityManager.createQuery(anyString(), eq(CalorieThresholdEntity.class))
                .setParameter(eq("userId"), anyLong())
                .setParameter(eq("date"), any(Date.class))
                .getSingleResult()).thenThrow(NoResultException.class);

        CalorieThresholdEntity result = repository.findByUserIdAndDate(1L, mockDate);
        assertNull(result);
    }

    @Test
    void testSave_NewEntity() {
        mockEntity.setThresholdId(0);

        doNothing().when(entityManager).persist(any(CalorieThresholdEntity.class));

        CalorieThresholdEntity result = repository.save(mockEntity);
        verify(entityManager, times(1)).persist(mockEntity);
        assertEquals(mockEntity, result);
    }

    @Test
    void testSave_UpdateEntity() {
        when(entityManager.merge(any(CalorieThresholdEntity.class))).thenReturn(mockEntity);

        CalorieThresholdEntity result = repository.save(mockEntity);
        verify(entityManager, times(1)).merge(mockEntity);
        assertEquals(mockEntity, result);
    }

    @Test
    void testDeleteById_Found() {
        when(entityManager.find(eq(CalorieThresholdEntity.class), anyLong())).thenReturn(mockEntity);
        doNothing().when(entityManager).remove(any(CalorieThresholdEntity.class));

        repository.deleteById(1L);
        verify(entityManager, times(1)).remove(mockEntity);
    }

    @Test
    void testDeleteById_NotFound() {
        when(entityManager.find(eq(CalorieThresholdEntity.class), anyLong())).thenReturn(null);

        repository.deleteById(1L);
        verify(entityManager, never()).remove(any(CalorieThresholdEntity.class));
    }

    @Test
    void testFindExceededThresholdDates() {
        List<Date> mockDates = List.of(mockDate);

//        when(entityManager.createQuery(anyString(), eq(Date.class)))
//                .thenReturn(Mockito.mock(javax.persistence.TypedQuery.class));
        when(entityManager.createQuery(anyString(), eq(Date.class))
                .setParameter(eq("userId"), anyLong())
                .setParameter(eq("startDate"), any(Date.class))
                .setParameter(eq("endDate"), any(Date.class))
                .getResultList()).thenReturn(mockDates);

        List<Date> result = repository.findExceededThresholdDates(1L, new Date(), new Date());
        assertNotNull(result);
        assertEquals(mockDates, result);
    }
}

