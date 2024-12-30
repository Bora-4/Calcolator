package com.calorator.controller;

import com.calorator.dto.UserDTO;
import com.calorator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO){
        if (!userService.isUsernameValid(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email not valid.");
        }
        if (!userService.isUsernameValid(userDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username not valid.");
        }
        if (!userService.isPasswordValid(userDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password not valid.");
        }
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO){
        UserDTO existingUser = userService.findById(userDTO.getId());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if (userDTO.getName() != null) {
            if (!userService.isUsernameValid(userDTO.getName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
            }
            existingUser.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            if (!userService.isEmailValid(userDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
            }
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            if (!userService.isPasswordValid(userDTO.getPassword())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Password not valid.");
            }
            existingUser.setPassword(userDTO.getPassword());
        }
        userService.update(existingUser);
        return ResponseEntity.ok("User updated successfully.");
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id){
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<UserDTO> findByName(@PathVariable("name") String name) {
        UserDTO userDTO = userService.findByName(name);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){

        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("validate/username/{username}")
    public ResponseEntity<Boolean> validateUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.validateUsername(username));
    }

    @GetMapping("validate/email/{email}")
    public ResponseEntity<Boolean> validateEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.validateEmail(email));
    }

    @GetMapping("validate/password/{password}")
    public ResponseEntity<Boolean> validatePassword(@PathVariable("password") String password) {
        return ResponseEntity.ok(userService.validatePassword(password));
    }

}
