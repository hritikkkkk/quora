package com.quora.quora.service;

import com.quora.quora.DTO.UserDTO;
import com.quora.quora.exception.DuplicateResourceException;
import com.quora.quora.exception.GlobalExceptionHandler;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.User;
import com.quora.quora.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {


        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + userDTO.getEmail());
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID userId, @Valid UserDTO dto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (!existingUser.getUsername().equals(dto.getUsername()) &&
                userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + dto.getUsername());
        }

        if (!existingUser.getEmail().equals(dto.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }
        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setBio(dto.getBio());

        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    public void followUser(UUID userId, UUID targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }

        User user = getUserById(userId);
        User targetUser = getUserById(targetUserId);

        if (user.getFollowing().contains(targetUser)) {
            throw new DuplicateResourceException("User is already following this user");
        }

        user.getFollowing().add(targetUser);
        userRepository.save(user);
    }

    public void unfollowUser(UUID userId, UUID targetUserId) {
        User user = getUserById(userId);
        User targetUser = getUserById(targetUserId);

        user.getFollowing().remove(targetUser);
        userRepository.save(user);

    }

    public List<User> getFollowers(UUID userId) {
        User user = getUserById(userId);
        return new ArrayList<>(user.getFollowers());
    }

    public List<User> getFollowing(UUID userId) {
        User user = getUserById(userId);
        return new ArrayList<>(user.getFollowing());
    }

    public List<User> searchUsers(String username) {
        if (username == null || username.trim().isEmpty()) {
            return getAllUsers();
        }
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }


}
