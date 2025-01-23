package com.calorator.repository;

import com.calorator.entity.ReportEntity;
import com.calorator.repository.impl.ReportRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

class ReportRepositoryImplTest {

    @InjectMocks
    private ReportRepositoryImpl repository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<ReportEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ReportEntity report = new ReportEntity();
        repository.save(report);
        verify(entityManager, times(1)).persist(report);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        ReportEntity report = new ReportEntity();
        when(entityManager.find(ReportEntity.class, id)).thenReturn(report);

        ReportEntity result = repository.findById(id);

        assertNotNull(result);
        assertEquals(report, result);
        verify(entityManager, times(1)).find(ReportEntity.class, id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(entityManager.find(ReportEntity.class, id)).thenReturn(null);

        ReportEntity result = repository.findById(id);

        assertNull(result);
        verify(entityManager, times(1)).find(ReportEntity.class, id);
    }

    @Test
    void testFindAll() {
        List<ReportEntity> reports = Arrays.asList(new ReportEntity(), new ReportEntity());
        when(entityManager.createQuery(anyString(), eq(ReportEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(reports);

        List<ReportEntity> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testFindAll_EmptyResult() {
        when(entityManager.createQuery(anyString(), eq(ReportEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<ReportEntity> result = repository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testUpdate() {
        ReportEntity report = new ReportEntity();
        repository.update(report);
        verify(entityManager, times(1)).merge(report);
    }

    @Test
    void testUpdate_NullEntity() {
        assertThrows(NullPointerException.class, () -> repository.update(null));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        ReportEntity report = new ReportEntity();
        when(entityManager.find(ReportEntity.class, id)).thenReturn(report);

        repository.delete(id);

        verify(entityManager, times(1)).remove(report);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 1L;
        when(entityManager.find(ReportEntity.class, id)).thenReturn(null);

        repository.delete(id);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByReportDate() {
        String reportDate = "2023-01-01";
        ReportEntity report = new ReportEntity();
        when(entityManager.createQuery(anyString(), eq(ReportEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("reportDate", reportDate)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(report);

        Optional<ReportEntity> result = repository.findByReportDate(reportDate);

        assertTrue(result.isPresent());
        assertEquals(report, result.get());
    }

    @Test
    void testFindByReportDate_NotFound() {
        String reportDate = "2023-01-01";
        when(entityManager.createQuery(anyString(), eq(ReportEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("reportDate", reportDate)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> repository.findByReportDate(reportDate));
        assertEquals("Error while fetching report by date", exception.getMessage());
    }
}
