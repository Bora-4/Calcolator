package com.calorator.service;

import com.calorator.dto.UserDTO;

import java.util.List;

public interface UserService {
    void save(UserDTO userDTO);
    UserDTO findById(Long id);
    UserDTO findByName(String name);
    void update(UserDTO userDTO);
    List<UserDTO> findAll();
    void delete(Long id);
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePassword(String password);
    boolean isEmailValid(String email);
    boolean isPasswordValid(String password);
    boolean isUsernameValid(String username);
    boolean authenticate(String username, String password);

}
