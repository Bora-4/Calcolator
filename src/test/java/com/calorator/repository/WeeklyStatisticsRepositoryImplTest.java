package com.calorator.repository;

import com.calorator.entity.WeeklyStatisticsEntity;
import com.calorator.repository.impl.WeeklyStatisticsRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeeklyStatisticsRepositoryImplTest {

    private WeeklyStatisticsRepositoryImpl weeklyStatisticsRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<WeeklyStatisticsEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weeklyStatisticsRepository = new WeeklyStatisticsRepositoryImpl();
        weeklyStatisticsRepository.entityManager = entityManager; // Inject mock EntityManager
    }

    @Test
    void testSave() {
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        weeklyStatisticsRepository.save(entity);
        verify(entityManager, times(1)).persist(entity);
    }

    @Test
    void testFindByIdWhenEntityExists() {
        Long id = 1L;
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(entity);

        WeeklyStatisticsEntity result = weeklyStatisticsRepository.findById(id);
        assertEquals(entity, result);
        verify(entityManager, times(1)).find(WeeklyStatisticsEntity.class, id);
    }

    @Test
    void testFindByIdWhenEntityDoesNotExist() {
        Long id = 1L;
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(null);

        WeeklyStatisticsEntity result = weeklyStatisticsRepository.findById(id);
        assertNull(result);
        verify(entityManager, times(1)).find(WeeklyStatisticsEntity.class, id);
    }

    @Test
    void testFindAll() {
        WeeklyStatisticsEntity entity1 = new WeeklyStatisticsEntity();
        WeeklyStatisticsEntity entity2 = new WeeklyStatisticsEntity();
        List<WeeklyStatisticsEntity> entities = Arrays.asList(entity1, entity2);

        when(entityManager.createQuery("SELECT w FROM WeeklyStatisticsEntity w", WeeklyStatisticsEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);

        List<WeeklyStatisticsEntity> result = weeklyStatisticsRepository.findAll();
        assertEquals(2, result.size());
        assertEquals(entity1, result.get(0));
        assertEquals(entity2, result.get(1));

        verify(entityManager, times(1)).createQuery("SELECT w FROM WeeklyStatisticsEntity w", WeeklyStatisticsEntity.class);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        weeklyStatisticsRepository.update(entity);
        verify(entityManager, times(1)).merge(entity);
    }

    @Test
    void testDeleteWhenEntityExists() {
        Long id = 1L;
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(entity);

        weeklyStatisticsRepository.delete(id);
        verify(entityManager, times(1)).remove(entity);
    }

    @Test
    void testDeleteWhenEntityDoesNotExist() {
        Long id = 1L;
        when(entityManager.find(WeeklyStatisticsEntity.class, id)).thenReturn(null);

        weeklyStatisticsRepository.delete(id);
        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByStatisticNameWhenEntityExists() {
        String statisticName = "TestStatistic";
        WeeklyStatisticsEntity entity = new WeeklyStatisticsEntity();
        when(entityManager.createQuery("SELECT statistic FROM WeeklyStatisticsEntity  statistic WHERE statistic.statisticName = :statisticName", WeeklyStatisticsEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("statisticName", statisticName)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(entity);

        WeeklyStatisticsEntity result = weeklyStatisticsRepository.findByStatisticName(statisticName);
        assertEquals(entity, result);

        verify(entityManager, times(1)).createQuery("SELECT statistic FROM WeeklyStatisticsEntity  statistic WHERE statistic.statisticName = :statisticName", WeeklyStatisticsEntity.class);
        verify(typedQuery, times(1)).setParameter("statisticName", statisticName);
        verify(typedQuery, times(1)).getSingleResult();
    }

    @Test
    void testFindByStatisticNameWhenEntityDoesNotExist() {
        String statisticName = "NonExistentStatistic";
        when(entityManager.createQuery("SELECT statistic FROM WeeklyStatisticsEntity  statistic WHERE statistic.statisticName = :statisticName", WeeklyStatisticsEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter("statisticName", statisticName)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new RuntimeException());

        WeeklyStatisticsEntity result = weeklyStatisticsRepository.findByStatisticName(statisticName);
        assertNull(result);

        verify(entityManager, times(1)).createQuery("SELECT statistic FROM WeeklyStatisticsEntity  statistic WHERE statistic.statisticName = :statisticName", WeeklyStatisticsEntity.class);
        verify(typedQuery, times(1)).setParameter("statisticName", statisticName);
        verify(typedQuery, times(1)).getSingleResult();
    }
}
