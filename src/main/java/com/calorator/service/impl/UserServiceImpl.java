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

    public boolean authenticate(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
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
    public UserDTO findById(int id) {
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
    public UserDTO findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException("User with email " + email + " not found.");
        }

        // Map UserEntity to UserDto (can use a utility method or library like MapStruct)
        UserEntity userEntity = userEntityOptional.get();
        return (UserMapper.toDTO(userEntity));
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
    public void delete(int id) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new EntityNotFoundException("User with id " + id + "was not found.");
        }
        userRepository.delete(id);
    }

    @Override
    public boolean isEmailValid(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return false;
        }
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    @Override
    public boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(passwordPattern);
    }

    @Override
    public boolean isUsernameValid(String username) {
        if (userRepository.findByUserName(username).isPresent()) {
            return false;
        }
        String usernamePattern = "^[a-zA-Z0-9_-]{3,15}$";
        return usernamePattern.matches(username);
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
