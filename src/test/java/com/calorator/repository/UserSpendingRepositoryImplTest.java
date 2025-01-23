package com.calorator.repository;

import com.calorator.entity.UserSpendingEntity;
import com.calorator.repository.impl.UserSpendingRepositoryImpl;
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

class UserSpendingRepositoryImplTest {

    private UserSpendingRepositoryImpl userSpendingRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<UserSpendingEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSpendingRepository = new UserSpendingRepositoryImpl();
        userSpendingRepository.entityManager = entityManager;
    }

    @Test
    void testSave() {
        UserSpendingEntity entity = new UserSpendingEntity();
        userSpendingRepository.save(entity);
        verify(entityManager, times(1)).persist(entity);
    }

    @Test
    void testUpdate() {
        UserSpendingEntity entity = new UserSpendingEntity();
        userSpendingRepository.update(entity);
        verify(entityManager, times(1)).merge(entity);
    }

    @Test
    void testDeleteWhenEntityExists() {
        Long spendingId = 1L;
        UserSpendingEntity entity = new UserSpendingEntity();
        when(entityManager.find(UserSpendingEntity.class, spendingId)).thenReturn(entity);

        userSpendingRepository.delete(spendingId);
        verify(entityManager, times(1)).remove(entity);
    }

    @Test
    void testDeleteWhenEntityDoesNotExist() {
        Long spendingId = 1L;
        when(entityManager.find(UserSpendingEntity.class, spendingId)).thenReturn(null);

        userSpendingRepository.delete(spendingId);
        verify(entityManager, never()).remove(any());
    }

    @Test
    void testFindByIdWhenEntityExists() {
        Long spendingId = 1L;
        UserSpendingEntity entity = new UserSpendingEntity();
        when(entityManager.find(UserSpendingEntity.class, spendingId)).thenReturn(entity);

        UserSpendingEntity result = userSpendingRepository.findById(spendingId);
        assertEquals(entity, result);
        verify(entityManager, times(1)).find(UserSpendingEntity.class, spendingId);
    }

    @Test
    void testFindByIdWhenEntityDoesNotExist() {
        Long spendingId = 1L;
        when(entityManager.find(UserSpendingEntity.class, spendingId)).thenReturn(null);

        UserSpendingEntity result = userSpendingRepository.findById(spendingId);
        assertNull(result);
        verify(entityManager, times(1)).find(UserSpendingEntity.class, spendingId);
    }

    @Test
    void testFindAll() {
        UserSpendingEntity entity1 = new UserSpendingEntity();
        UserSpendingEntity entity2 = new UserSpendingEntity();
        List<UserSpendingEntity> entities = Arrays.asList(entity1, entity2);

        when(entityManager.createQuery("SELECT u FROM UserSpendingEntity u", UserSpendingEntity.class))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);

        List<UserSpendingEntity> result = userSpendingRepository.findAll();
        assertEquals(2, result.size());
        assertEquals(entity1, result.get(0));
        assertEquals(entity2, result.get(1));

        verify(entityManager, times(1)).createQuery("SELECT u FROM UserSpendingEntity u", UserSpendingEntity.class);
        verify(typedQuery, times(1)).getResultList();
    }
}
