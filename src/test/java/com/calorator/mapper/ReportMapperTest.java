package com.calorator.mapper;

import com.calorator.dto.ReportDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReportMapperTest {

    @Test
    void testToEntity_validReportDTOProvided_validUserEntityProvided(){

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(1L);
        reportDTO.setReportDate(LocalDate.of(2025, 1, 12));
        reportDTO.setCreatedAt(LocalDateTime.of(2025, 1, 10, 12, 0));
        reportDTO.setUpdatedAt(LocalDateTime.of(2025, 1, 11, 14, 0));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");

        ReportEntity report = ReportMapper.toEntity(reportDTO, user);

        assertNotNull(report);
        assertEquals(reportDTO.getId(), report.getId());
        assertEquals(reportDTO.getReportDate(), report.getReportDate());
        assertEquals(reportDTO.getCreatedAt(), report.getCreatedAt());
        assertEquals(reportDTO.getUpdatedAt(), report.getUpdatedAt());
        assertEquals(user, report.getAdmin());
    }

    @Test
    void testToEntity_validReportDTOProvided_nullUserEntityProvided(){
        ReportDTO reportDTO = new ReportDTO();
        assertThrows(NullPointerException.class, ()-> ReportMapper.toEntity(reportDTO, null));
    }

    @Test
    void testToEntity_nullReportDTOProvided_nullUserEntityProvided(){
        UserEntity user = new UserEntity();
        assertThrows(NullPointerException.class, ()-> ReportMapper.toEntity(null, user));
    }

    @Test
    void testToDTO_validReportEntityProvided(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");
        user.setRole(UserEntity.Role.admin);

        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setAdmin(user);
        report.setReportDate(LocalDate.of(2025, 1, 12));
        report.setCreatedAt(LocalDateTime.of(2025, 1, 10, 12, 0));
        report.setUpdatedAt(LocalDateTime.of(2025, 1, 11, 14, 0));

        ReportDTO reportDTO = ReportMapper.toDTO(report);

        assertNotNull(reportDTO);
        assertEquals(report.getUpdatedAt(), reportDTO.getUpdatedAt());
        assertEquals(report.getReportDate(), reportDTO.getReportDate());
        assertEquals(report.getCreatedAt(), reportDTO.getCreatedAt());
        assertEquals(report.getUpdatedAt(), reportDTO.getUpdatedAt());
        assertNotNull(reportDTO.getAdmin());
        assertEquals(user.getId(), reportDTO.getAdmin().getId());

    }

    @Test
    void testToDTO_nullReportEntityProvided(){
        assertThrows(NullPointerException.class, ()-> ReportMapper.toDTO(null));
    }
}
