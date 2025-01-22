package com.calorator.controller;

import com.calorator.dto.ReportDTO;
import com.calorator.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody ReportDTO reportDTO) {
        try {
            reportService.save(reportDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Report created successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateReport(@RequestBody ReportDTO reportDTO) {
        try {
            ReportDTO existingReport = reportService.findById(reportDTO.getId());
            if (existingReport == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"Report not found.\"}");
            }
            if (reportDTO.getAdmin() != null) {
                existingReport.setAdmin(reportDTO.getAdmin());
            }
            if (reportDTO.getReportDate() != null) {
                existingReport.setReportDate(reportDTO.getReportDate());
            }
            reportService.update(existingReport);
            return ResponseEntity.ok("{\"message\":\"Report updated successfully.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> findAllReports() {
        try {
            return ResponseEntity.ok(reportService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ReportDTO> findReportById(@PathVariable("id") Long id) {
        try {
            ReportDTO reportDTO = reportService.findById(id);
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("date/{reportDate}")
    public ResponseEntity<ReportDTO> findReportByDate(@PathVariable("reportDate") String reportDate) {
        try {
            ReportDTO reportDTO = reportService.findByReportDate(reportDate);
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable("id") Long id) {
        try {
            reportService.delete(id);
            return ResponseEntity.ok("{\"message\":\"Report deleted successfully.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
