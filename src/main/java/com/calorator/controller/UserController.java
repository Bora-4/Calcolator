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
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
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
}
