package com.quora.quora.controller;

import com.quora.quora.DTO.UserDTO;
import com.quora.quora.models.User;
import com.quora.quora.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {

        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserDTO userDTO) {

        User updatedUser = userService.updateUser(id, userDTO);


        UserDTO response = UserDTO.builder()
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .bio(updatedUser.getBio())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<Map<String, String>> followUser(
            @PathVariable UUID userId,
            @PathVariable UUID targetUserId) {
        userService.followUser(userId, targetUserId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User followed successfully"));
    }


    @DeleteMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<Map<String, String>> unfollowUser(
            @PathVariable UUID userId,
            @PathVariable UUID targetUserId) {
        userService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok(Map.of("message", "User unfollowed successfully"));
    }
}
