package com.calorator.mapper;

import com.calorator.dto.MonthlyExpenditureDTO;
import com.calorator.entity.MonthlyExpenditureEntity;
import com.calorator.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MonthlyExpenditureMapperTest {
    @Test
    void testToEntity_validMonthlyExpenditureDTOProvided_validUserEntityProvided() {

        MonthlyExpenditureDTO expenditureDTO = new MonthlyExpenditureDTO();
        expenditureDTO.setId(1L);
        expenditureDTO.setMonth(LocalDate.of(2025, 1, 12));
        expenditureDTO.setTotalSpent(BigDecimal.valueOf(500.154));
        expenditureDTO.setWarning(true);
        expenditureDTO.setCreatedAt(LocalDateTime.of(2025, 1, 10, 12, 0));
        expenditureDTO.setUpdatedAt(LocalDateTime.of(2025, 1, 11, 14, 0));

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@gmail.com");

        MonthlyExpenditureEntity expenditureEntity = MonthlyExpenditureMapper.toEntity(expenditureDTO, user);

        assertNotNull(expenditureEntity);
        assertEquals(expenditureDTO.getId(), expenditureEntity.getId());
        assertEquals(expenditureDTO.getMonth(), expenditureEntity.getMonth());
        assertEquals(user, expenditureEntity.getUser());
        assertEquals(expenditureDTO.getTotalSpent(), expenditureEntity.getTotalSpent());
        assertEquals(expenditureDTO.isWarning(), expenditureEntity.isWarning());
        assertEquals(expenditureDTO.getCreatedAt(), expenditureEntity.getCreatedAt());
        assertEquals(expenditureDTO.getUpdatedAt(), expenditureEntity.getUpdatedAt());
    }

    @Test
    void testToEntity_nullMonthlyExpenditureDTOProvided_validUserEntityProvided() {

        UserEntity user = new UserEntity();

        assertThrows(NullPointerException.class, () -> MonthlyExpenditureMapper.toEntity(null, user));
    }

    @Test
    void testToEntity_validMonthlyExpenditureDTOProvided_nullUserEntityProvided() {

        MonthlyExpenditureDTO expenditureDTO = new MonthlyExpenditureDTO();

        assertThrows(NullPointerException.class, () -> MonthlyExpenditureMapper.toEntity(expenditureDTO, null));
    }

    @Test
    void testToDTO_validMonthlyExpenditureEntityProvided() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@gmail.com");
        user.setRole(UserEntity.Role.user);

        MonthlyExpenditureEntity expenditureEntity = new MonthlyExpenditureEntity();
        expenditureEntity.setId(1L);
        expenditureEntity.setUser(user);
        expenditureEntity.setMonth(LocalDate.of(2025, 1, 12));
        expenditureEntity.setTotalSpent(BigDecimal.valueOf(500.154));
        expenditureEntity.setWarning(true);
        expenditureEntity.setCreatedAt(LocalDateTime.of(2025, 1, 10, 12, 0));
        expenditureEntity.setUpdatedAt(LocalDateTime.of(2025, 1, 11, 14, 0));

        MonthlyExpenditureDTO expenditureDTO = MonthlyExpenditureMapper.toDTO(expenditureEntity);

        assertNotNull(expenditureDTO);
        assertEquals(expenditureEntity.getId(), expenditureDTO.getId());
        assertEquals(expenditureEntity.getMonth(), expenditureDTO.getMonth());
        assertEquals(expenditureEntity.getTotalSpent(), expenditureDTO.getTotalSpent());
        assertEquals(expenditureEntity.isWarning(), expenditureDTO.isWarning());
        assertEquals(expenditureEntity.getCreatedAt(), expenditureDTO.getCreatedAt());
        assertEquals(expenditureEntity.getUpdatedAt(), expenditureDTO.getUpdatedAt());
        assertNotNull(expenditureDTO.getUser());
        assertEquals(user.getId(), expenditureDTO.getUser().getId());
    }

    @Test
    void testToDTO_nullMonthlyExpenditureEntityProvided() {

        assertThrows(NullPointerException.class, () -> MonthlyExpenditureMapper.toDTO(null));
    }
}
