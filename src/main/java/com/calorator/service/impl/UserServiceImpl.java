package com.calorator.service.impl;

import com.calorator.dto.UserDTO;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.UserRepository;
import com.calorator.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        Optional<UserEntity> user = userRepository.findByUserName(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public void save(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO findById(Long id) {
        UserEntity userEntity = this.userRepository.findById(id);
        if (userEntity != null){
            return UserMapper.toDTO(userEntity);
        }
        throw new EntityNotFoundException("User with id " + id + " was not found.");
    }

    @Override
    public UserDTO findByName(String name) {
        UserEntity userEntity = this.userRepository.findByName(name);
        if (userEntity != null){
            return UserMapper.toDTO(userEntity);
        }
        throw new EntityNotFoundException("User with name " + name + "was not found.");
    }

    @Override
    public void update(UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(userDTO.getId());
        if (existingUser != null) {
            UserEntity updatedUser = UserMapper.toEntity(userDTO);
            userRepository.update(updatedUser);
        } else {
            throw new EntityNotFoundException("User with id " + userDTO.getId() + " was not found.");
        }
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new EntityNotFoundException("User with id " + id + "was not found.");
        }
        userRepository.delete(id);
    }

    @Override
    public void isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }
    }

    @Override
    public void isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException("Invalid password format.");
        }
    }

    @Override
    public void isUsernameValid(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        String usernamePattern = "^[a-zA-Z0-9_-]{3,15}$";
        if (!username.matches(usernamePattern)) {
            throw new IllegalArgumentException("Invalid username format.");
        }
        if (userRepository.findByUserName(username).isPresent()) {
            throw new IllegalStateException("Username already exists.");
        }
    }

    @Override
    public boolean validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validatePassword(String password) {
        if (userRepository.findByPassword(password).isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateUsername(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            return true;
        }
        return false;
    }
}
