package com.calorator.repository;

import com.calorator.entity.UserEntity;
import com.calorator.repository.impl.UserRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl repository;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<UserEntity> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        UserEntity user = new UserEntity();
        repository.save(user);
        verify(em, times(1)).persist(user);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        UserEntity user = new UserEntity();
        when(em.find(UserEntity.class, id)).thenReturn(user);

        UserEntity result = repository.findById(id);

        assertNotNull(result);
        assertEquals(user, result);
        verify(em, times(1)).find(UserEntity.class, id);
    }

    @Test
    void testFindByName() {
        String name = "John";
        UserEntity user = new UserEntity();
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        UserEntity result = repository.findByName(name);

        assertNotNull(result);
        assertEquals(user, result);
        verify(typedQuery, times(1)).getSingleResult();
    }

    @Test
    void testFindByName_NoResult() {
        String name = "John";
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        UserEntity result = repository.findByName(name);

        assertNull(result);
    }

    @Test
    void testUpdate() {
        UserEntity user = new UserEntity();
        repository.update(user);
        verify(em, times(1)).merge(user);
    }

    @Test
    void testFindAll() {
        List<UserEntity> users = Arrays.asList(new UserEntity(), new UserEntity());
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(users);

        List<UserEntity> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void testDelete() {
        Long id = 1L;
        UserEntity user = new UserEntity();
        when(em.find(UserEntity.class, id)).thenReturn(user);

        repository.delete(id);

        verify(em, times(1)).remove(user);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        UserEntity user = new UserEntity();
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("email", email)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        Optional<UserEntity> result = repository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByEmail_NoResult() {
        String email = "test@example.com";
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("email", email)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<UserEntity> result = repository.findByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUserName() {
        String name = "John";
        UserEntity user = new UserEntity();
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        Optional<UserEntity> result = repository.findByUserName(name);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByUserName_NoResult() {
        String name = "John";
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", name)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<UserEntity> result = repository.findByUserName(name);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByPassword() {
        String password = "password123";
        UserEntity user = new UserEntity();
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("password", password)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(user);

        Optional<UserEntity> result = repository.findByPassword(password);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByPassword_NoResult() {
        String password = "password123";
        when(em.createQuery(anyString(), eq(UserEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("password", password)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<UserEntity> result = repository.findByPassword(password);

        assertTrue(result.isEmpty());
    }
}
