package com.calorator.repository;

import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.repository.impl.WeeklyStatisticsRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeeklyStatisticsRepositoryImplTest {

    @InjectMocks
    private WeeklyStatisticsRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<WeeklyStatisticsEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        repository.save(entity);
        verify(entityManager, times(1)).persist(entity);
    }

    @Test
    void testSave_NullEntity() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }

    @Test
    void testFindById() {
        Long id = 1L;
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(entity);

        WeeklyStatisticsEntity result = repository.findById(id);

        assertNotNull(result);
        assertEquals(entity, result);
        verify(entityManager, times(1)).find(WeeklyStatisticsEntity.class, id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(null);

        WeeklyStatisticsEntity result = repository.findById(id);

        assertNull(result);
        verify(entityManager, times(1)).find(WeeklyStatisticsEntity.class, id);
    }

    @Test
    void testFindAll() {
        List<WeeklyStatisticsEntity> entities = Arrays.asList(new WeeklyStatisticsEntity(), new WeeklyStatisticsEntity());
        when(entityManager.createQuery(anyString(), eq(WeeklyStatisticsEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);

        List<WeeklyStatisticsEntity> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testFindAll_EmptyResult() {
        when(entityManager.createQuery(anyString(), eq(WeeklyStatisticsEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<WeeklyStatisticsEntity> result = repository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        repository.update(entity);
        verify(entityManager, times(1)).merge(entity);
    }

    @Test
    void testUpdate_NullEntity() {
        assertThrows(NullPointerException.class, () -> repository.update(null));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(entity);

        repository.delete(id);

        verify(entityManager, times(1)).remove(entity);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 1L;
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(null);

        repository.delete(id);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void testDelete_NullId() {
        assertThrows(IllegalArgumentException.class, () -> repository.delete(null));
    }

    @Test
    void testFindByStatisticName() {
        String statisticName = "CaloriesBurned";
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.createQuery(anyString(), eq(WeeklyStatisticsEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("statisticName", statisticName)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(entity));

        Optional<WeeklyStatisticsEntity> result = repository.findByStatisticName(statisticName);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
    }

    @Test
    void testFindByStatisticName_NotFound() {
        String statisticName = "CaloriesBurned";
        when(entityManager.createQuery(anyString(), eq(WeeklyStatisticsEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("statisticName", statisticName)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        Optional<WeeklyStatisticsEntity> result = repository.findByStatisticName(statisticName);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatisticName_NullName() {
        assertThrows(IllegalArgumentException.class, () -> repository.findByStatisticName(null));
    }
}
