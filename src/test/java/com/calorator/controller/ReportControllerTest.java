package com.calorator.controller;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.UserDTO;
import com.calorator.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    void testCreateReport_Success() throws Exception {
        // Krijo një UserDTO për admin
        UserDTO admin = new UserDTO();
        admin.setName("admin");
        admin.setEmail("admin@example.com");

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setAdmin(admin);

        doNothing().when(reportService).save(reportDTO);

        mockMvc.perform(post("/reports")
                        .contentType("application/json")
                        .content("{\"id\":1,\"admin\":{\"name\":\"admin\",\"email\":\"admin@example.com\"}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Report created successfully."));
    }

    @Test
    void testCreateReport_Failure() throws Exception {
        // Krijo një UserDTO për admin
        UserDTO admin = new UserDTO();
        admin.setName("admin");
        admin.setEmail("admin@example.com");

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setAdmin(admin);

        when(reportService.save(reportDTO)).thenThrow(new RuntimeException("Error creating report"));

        mockMvc.perform(post("/reports")
                        .contentType("application/json")
                        .content("{\"id\":1,\"admin\":{\"name\":\"admin\",\"email\":\"admin@example.com\"}}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Error creating report"));
    }

    @Test
    void testUpdateReport_Success() throws Exception {
        // Krijo një UserDTO për admin
        UserDTO admin = new UserDTO();
        admin.setName("admin");
        admin.setEmail("admin@example.com");

        ReportDTO existingReport = new ReportDTO();
        existingReport.setId(1L);
        existingReport.setAdmin(admin);

        ReportDTO updateReport = new ReportDTO();
        updateReport.setId(1L);
        // Krijo një UserDTO të ri për adminin që po përditësohet
        UserDTO newAdmin = new UserDTO();
        newAdmin.setName("new_admin");
        newAdmin.setEmail("new_admin@example.com");
        updateReport.setAdmin(newAdmin);

        when(reportService.findById(1L)).thenReturn(existingReport);
        doNothing().when(reportService).update(existingReport);

        mockMvc.perform(put("/reports")
                        .contentType("application/json")
                        .content("{\"id\":1,\"admin\":{\"name\":\"new_admin\",\"email\":\"new_admin@example.com\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report updated successfully."));
    }

    @Test
    void testUpdateReport_NotFound() throws Exception {
        // Krijo një UserDTO për adminin e ri
        UserDTO newAdmin = new UserDTO();
        newAdmin.setName("new_admin");
        newAdmin.setEmail("new_admin@example.com");

        ReportDTO updateReport = new ReportDTO();
        updateReport.setId(1L);
        updateReport.setAdmin(newAdmin);

        when(reportService.findById(1L)).thenReturn(null);

        mockMvc.perform(put("/reports")
                        .contentType("application/json")
                        .content("{\"id\":1,\"admin\":{\"name\":\"new_admin\",\"email\":\"new_admin@example.com\"}}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Report not found."));
    }

    @Test
    void testFindAllReports() throws Exception {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setAdmin("admin");

        when(reportService.findAll()).thenReturn(Collections.singletonList(reportDTO));

        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].admin").value("admin"));
    }

    @Test
    void testFindReportById_Success() throws Exception {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setAdmin("admin");

        when(reportService.findById(1L)).thenReturn(reportDTO);

        mockMvc.perform(get("/reports/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.admin").value("admin"));
    }

    @Test
    void testFindReportById_NotFound() throws Exception {
        when(reportService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/reports/id/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindReportByDate_Success() throws Exception {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportDate("2025-01-23");
        reportDTO.setAdmin("admin");

        when(reportService.findByReportDate("2025-01-23")).thenReturn(reportDTO);

        mockMvc.perform(get("/reports/date/{reportDate}", "2025-01-23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportDate").value("2025-01-23"));
    }

    @Test
    void testFindReportByDate_NotFound() throws Exception {
        when(reportService.findByReportDate("2025-01-23")).thenReturn(null);

        mockMvc.perform(get("/reports/date/{reportDate}", "2025-01-23"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteReport_Success() throws Exception {
        doNothing().when(reportService).delete(1L);

        mockMvc.perform(delete("/reports/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report deleted successfully."));
    }

    @Test
    void testDeleteReport_NotFound() throws Exception {
        doThrow(new RuntimeException("Report not found")).when(reportService).delete(1L);

        mockMvc.perform(delete("/reports/id/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Report not found"));
    }
}
