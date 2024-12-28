package com.calorator.mapper;

import com.calorator.dto.UserDTO;
import com.calorator.entity.UserEntity;

public class UserMapper {

    private UserMapper(){
        // private constructor
    }
    public static UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            throw new NullPointerException("UserDTO cannot be null.");
        }

        UserEntity user = new UserEntity();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(UserEntity.Role.valueOf(userDTO.getRole().toUpperCase()));

        return user;
    }

    public static UserDTO toDTO(UserEntity user) {
        if (user == null) {
            throw new NullPointerException("UserEntity cannot be null.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole().name());

        return userDTO;
    }
}
