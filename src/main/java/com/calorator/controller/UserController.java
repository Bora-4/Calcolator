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

    @GetMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDTO userDTO){
        if (userService.authenticate(userDTO.getEmail(), userDTO.getPassword())) {
            return ResponseEntity.ok("{\"message\":\"Login successful.\"}");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"Invalid credentials.\"}");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.isUsernameValid(userDTO.getName());
            userService.isEmailValid(userDTO.getEmail());
            userService.isPasswordValid(userDTO.getPassword());
            userService.save(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"User created successfully.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO) {
        try {
            UserDTO existingUser = userService.findById(userDTO.getId());
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"User not found.\"}");
            }
            if (userDTO.getName() != null) {
                userService.isUsernameValid(userDTO.getName());
                existingUser.setName(userDTO.getName());
            }
            if (userDTO.getEmail() != null) {
                userService.isEmailValid(userDTO.getEmail());
                existingUser.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null) {
                userService.isPasswordValid(userDTO.getPassword());
                existingUser.setPassword(userDTO.getPassword());
            }
            userService.update(existingUser);
            return ResponseEntity.ok("{\"message\":\"User updated successfully.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\":\"" + e.getMessage() + "\"}");
        }
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
        return ResponseEntity.ok("{\"message\":\"User deleted successfully.\"}");
    }
}
