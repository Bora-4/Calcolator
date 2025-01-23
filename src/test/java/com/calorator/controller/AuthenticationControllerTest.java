package com.calorator.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    // Test for Home Page
    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(view().name("home"));
    }

    // Test for Login Page
    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    // Test for Signup Page
    @Test
    void testSignupPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(view().name("signup"));
    }

    // Test for Dashboard with valid session
    @Test
    void testDashboardWithValidSession() throws Exception {
        // Simulate an authenticated user (mocking session)
        when(session.getAttribute("userId")).thenReturn("12345");

        mockMvc.perform(get("/dashboard").sessionAttr("userId", "12345"))
                .andExpect(view().name("dashboard"));
    }

    // Test for Dashboard without session (redirect to login)
    @Test
    void testDashboardWithoutSession() throws Exception {
        // Simulating no user logged in
        when(session.getAttribute("userId")).thenReturn(null);

        mockMvc.perform(get("/dashboard"))
                .andExpect(redirectedUrl("/login"));
    }

    // Test for Admin with valid session
    @Test
    void testAdminWithValidSession() throws Exception {
        // Simulate an authenticated user (mocking session)
        when(session.getAttribute("userId")).thenReturn("12345");

        mockMvc.perform(get("/admin").sessionAttr("userId", "12345"))
                .andExpect(view().name("admin"));
    }

    // Test for Admin without session (redirect to login)
    @Test
    void testAdminWithoutSession() throws Exception {
        // Simulating no user logged in
        when(session.getAttribute("userId")).thenReturn(null);

        mockMvc.perform(get("/admin"))
                .andExpect(redirectedUrl("/login"));
    }

    // Test for Logout
    @Test
    void testLogout() throws Exception {
        // Simulating an authenticated session
        mockMvc.perform(get("/logout").sessionAttr("userId", "12345"))
                .andExpect(redirectedUrl("/home"));
    }
}

