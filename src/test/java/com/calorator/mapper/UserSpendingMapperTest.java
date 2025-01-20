package com.calorator.mapper;
import com.calorator.dto.UserSpendingDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.entity.UserSpendingEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class UserSpendingMapperTest {

    @Test
    void testToEntity_validUserSpendingDTOProvided_validUserEntityProvided_validReportEntityProvided(){

        UserSpendingDTO userSpendingDTO = new UserSpendingDTO();
        userSpendingDTO.setId(1L);
        userSpendingDTO.setTotalSpent(BigDecimal.valueOf(275.88));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");

        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setReportDate(LocalDate.of(2025, 1, 15));

        UserSpendingEntity userSpendingEntity = UserSpendingMapper.toEntity(userSpendingDTO, user, report);

        assertNotNull(userSpendingEntity);
        assertEquals(userSpendingDTO.getId(), userSpendingEntity.getId());
        assertEquals(user, userSpendingEntity.getUser());
        assertEquals(report, userSpendingEntity.getReport());
        assertEquals(userSpendingDTO.getTotalSpent(), userSpendingEntity.getTotalSpent());

    }

    @Test
    void testToEntity_validUserSpendingDTOProvided_nullUserEntityProvided_nullReportEntityProvided(){
        UserSpendingDTO spendingDTO = new UserSpendingDTO();
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toEntity(spendingDTO, null, null));
    }

    @Test
    void testToEntity_validUserSpendingDTOProvided_validUserEntityProvided_nullReportDTOProvided(){
        UserSpendingDTO spendingDTO = new UserSpendingDTO();
        UserEntity user = new UserEntity();
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toEntity(spendingDTO, user, null));
    }

    @Test
    void testToEntity_validUserSpendingDTOProvided_nullUserEntityProvided_validReportDTOProvided(){
        UserSpendingDTO spendingDTO = new UserSpendingDTO();
        ReportEntity report = new ReportEntity();
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toEntity(spendingDTO, null, report));
    }

    @Test
    void testToEntity_nullUserSpendingDTOProvided_validUserEntityProvided_validReportDTOProvided(){
        UserEntity user = new UserEntity();
        ReportEntity report = new ReportEntity();
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toEntity(null, user, report));
    }

    @Test
    void testToEntity_nullUserSpendingDTOProvided_nullUserEntityProvided_nullReportDTOProvided(){
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toEntity(null, null, null));
    }

    @Test
    void testToDTO_validUserSpendingEntityProvided(){
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");
        user.setRole(UserEntity.Role.admin);

        ReportEntity report = new ReportEntity();
        report.setId(1L);
        report.setReportDate(LocalDate.of(2025, 1, 15));


        UserSpendingEntity userSpendingEntity = new UserSpendingEntity();
        userSpendingEntity.setId(1L);
        userSpendingEntity.setUser(user);
        userSpendingEntity.setReport(report);
        userSpendingEntity.setTotalSpent(BigDecimal.valueOf(225.88));

        UserSpendingDTO userSpendingDTO = UserSpendingMapper.toDTO(userSpendingEntity);
        assertNotNull(userSpendingDTO);
        assertEquals(userSpendingEntity.getId(), userSpendingDTO.getId());
        assertEquals(userSpendingEntity.getTotalSpent(), userSpendingDTO.getTotalSpent());
        assertNotNull(userSpendingDTO.getUser());
        assertNotNull(userSpendingDTO.getReport());
        assertEquals(user.getId(), userSpendingDTO.getUser().getId());
        assertEquals(report.getId(), userSpendingDTO.getReport().getId());

    }

    @Test
    void testToDTO_nullUserSpendingEntityProvided(){
        assertThrows(NullPointerException.class, () -> UserSpendingMapper.toDTO(null));
    }



}
