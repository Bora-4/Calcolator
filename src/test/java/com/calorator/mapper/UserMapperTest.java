package com.calorator.mapper;

import com.calorator.dto.UserDTO;
import com.calorator.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    void testToEntity_validUserDTOProvided(){

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("borarrena");
        userDTO.setEmail("bora.rrena@fti.edu.al");
        userDTO.setRole("admin");
        userDTO.setPassword("Password.2025");

        UserEntity userEntity = UserMapper.toEntity(userDTO);

        assertNotNull(userEntity);
        assertEquals(userDTO.getId(), userEntity.getId());
        assertEquals(userDTO.getName(), userEntity.getName());
        assertEquals(userDTO.getEmail(), userEntity.getEmail());
        assertEquals(userDTO.getRole(), userEntity.getRole().name());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());

    }

    @Test
    void testToEntity_nullUserDTOProvided(){
        assertThrows(NullPointerException.class, () -> UserMapper.toEntity(null));
    }

    @Test
    void testToDTO_validUserEntityProvided(){

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("borarrena");
        user.setEmail("bora.rrena@fti.edu.al");
        user.setRole(UserEntity.Role.admin);
        user.setPassword("Password.2025");

        UserDTO userDTO = UserMapper.toDTO(user);

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getRole().name(), userDTO.getRole());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    void testToDTO_nullUserEntityProvided(){
        assertThrows(NullPointerException.class, () -> UserMapper.toDTO(null));
    }
}
