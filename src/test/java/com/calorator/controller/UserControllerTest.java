package com.calorator.controller;

import com.calorator.dto.UserDTO;
import com.calorator.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setRole("user");

        when(userService.authenticate(userDTO.getEmail(), userDTO.getPassword())).thenReturn(true);
        when(userService.findByEmail(userDTO.getEmail())).thenReturn(userDTO);

        ResponseEntity<String> response = userController.authenticateUser(userDTO, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Login successful"));
        verify(session, times(1)).setAttribute("userId", 1L);
        verify(session, times(1)).setAttribute("user", "Test User");
        verify(session, times(1)).setAttribute("role", "user");
    }

    @Test
    void testAuthenticateUser_Failure() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("invalid@example.com");
        userDTO.setPassword("wrongpassword");

        when(userService.authenticate(userDTO.getEmail(), userDTO.getPassword())).thenReturn(false);

        ResponseEntity<String> response = userController.authenticateUser(userDTO, session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Invalid credentials"));
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("New User");
        userDTO.setEmail("newuser@example.com");
        userDTO.setPassword("password");

        doNothing().when(userService).isUsernameValid(userDTO.getName());
        doNothing().when(userService).isEmailValid(userDTO.getEmail());
        doNothing().when(userService).isPasswordValid(userDTO.getPassword());
        doNothing().when(userService).save(userDTO);

        ResponseEntity<String> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User created successfully"));
    }

    @Test
    void testCreateUser_InvalidData() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Invalid User");
        userDTO.setEmail("invalid-email");
        userDTO.setPassword("123");

        doThrow(new IllegalArgumentException("Invalid email format")).when(userService).isEmailValid(userDTO.getEmail());

        ResponseEntity<String> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Invalid email format"));
    }

    @Test
    void testUpdateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Updated User");
        userDTO.setEmail("updated@example.com");
        userDTO.setPassword("newpassword");

        UserDTO existingUser = new UserDTO();
        existingUser.setId(1L);
        existingUser.setName("Old User");
        existingUser.setEmail("old@example.com");
        existingUser.setPassword("oldpassword");

        when(userService.findById(userDTO.getId())).thenReturn(existingUser);
        doNothing().when(userService).isUsernameValid(userDTO.getName());
        doNothing().when(userService).isEmailValid(userDTO.getEmail());
        doNothing().when(userService).isPasswordValid(userDTO.getPassword());
        doNothing().when(userService).update(any(UserDTO.class));

        ResponseEntity<String> response = userController.update(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User updated successfully"));
    }

    @Test
    void testUpdateUser_NotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(999L);

        when(userService.findById(userDTO.getId())).thenReturn(null);

        ResponseEntity<String> response = userController.update(userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User not found"));
    }

    @Test
    void testFindAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO());
        users.add(new UserDTO());

        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testFindUserById() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        when(userService.findById(1L)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void testFindUserByName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");

        when(userService.findByName("Test User")).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.findByName("Test User");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test User", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).delete(1L);

        ResponseEntity<String> response = userController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("User deleted successfully"));
    }
}
