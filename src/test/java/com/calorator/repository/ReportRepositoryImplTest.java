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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportRepositoryImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ReportRepositoryImpl reportRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ReportEntity reportEntity = new ReportEntity();
        reportRepository.save(reportEntity);
        verify(em, times(1)).persist(reportEntity);
    }

    @Test
    void testFindById_Found() {
        Long id = 1L;
        ReportEntity reportEntity = new ReportEntity();
        when(em.find(ReportEntity.class, id)).thenReturn(reportEntity);

        ReportEntity result = reportRepository.findById(id);

        assertNotNull(result);
        assertEquals(reportEntity, result);
        verify(em, times(1)).find(ReportEntity.class, id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(em.find(ReportEntity.class, id)).thenReturn(null);

        ReportEntity result = reportRepository.findById(id);

        assertNull(result);
        verify(em, times(1)).find(ReportEntity.class, id);
    }

    @Test
    void testFindAll() {
        // Mock the TypedQuery behavior
        TypedQuery<ReportEntity> query = mock(TypedQuery.class);
        List<ReportEntity> reportEntities = List.of(new ReportEntity(), new ReportEntity());

        when(em.createQuery("SELECT r FROM ReportEntity r", ReportEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(reportEntities);

        // Call the method
        List<ReportEntity> result = reportRepository.findAll();

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(em, times(1)).createQuery("SELECT r FROM ReportEntity r", ReportEntity.class);
        verify(query, times(1)).getResultList();
    }


    @Test
    void testUpdate() {
        ReportEntity reportEntity = new ReportEntity();
        reportRepository.update(reportEntity);
        verify(em, times(1)).merge(reportEntity);
    }


    @Test
    void testFindByReportDate_Found() {
        String reportDate = "2023-12-31";
        TypedQuery<ReportEntity> query = mock(TypedQuery.class);
        ReportEntity reportEntity = new ReportEntity();

        when(em.createQuery("SELECT r FROM ReportEntity r WHERE r.reportDate = :reportDate", ReportEntity.class)).thenReturn(query);
        when(query.setParameter("reportDate", reportDate)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(reportEntity);

        // Call the method
        ReportEntity result = reportRepository.findByReportDate(reportDate);

        // Verify the result
        assertNotNull(result);
        assertEquals(reportEntity, result);
        verify(em, times(1)).createQuery("SELECT r FROM ReportEntity r WHERE r.reportDate = :reportDate", ReportEntity.class);
        verify(query, times(1)).setParameter("reportDate", reportDate);
        verify(query, times(1)).getSingleResult();
    }



    @Test
    void testFindByReportDate_NotFound() {
        String reportDate = "2023-12-31";
        TypedQuery<ReportEntity> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT r FROM ReportEntity r WHERE r.reportDate = :reportDate", ReportEntity.class)).thenReturn(query);
        when(query.setParameter("reportDate", reportDate)).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new NoResultException());

        // Call the method
        ReportEntity result = reportRepository.findByReportDate(reportDate);

        // Verify the result
        assertNull(result);
        verify(em, times(1)).createQuery("SELECT r FROM ReportEntity r WHERE r.reportDate = :reportDate", ReportEntity.class);
        verify(query, times(1)).setParameter("reportDate", reportDate);
        verify(query, times(1)).getSingleResult();
    }


    @Test
    void testDelete_Existing() {
        Long id = 1L;
        ReportEntity reportEntity = new ReportEntity();
        when(em.find(ReportEntity.class, id)).thenReturn(reportEntity);

        reportRepository.delete(id);

        verify(em, times(1)).find(ReportEntity.class, id);
        verify(em, times(1)).remove(reportEntity);
    }

    @Test
    void testDelete_NotExisting() {
        Long id = 1L;
        when(em.find(ReportEntity.class, id)).thenReturn(null);

        reportRepository.delete(id);

        verify(em, times(1)).find(ReportEntity.class, id);
        verify(em, never()).remove(any());
    }
}
