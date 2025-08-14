package com.quora.quora.service;

import com.quora.quora.DTO.UserDTO;
import com.quora.quora.exception.DuplicateResourceException;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.User;
import com.quora.quora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + user.getEmail());
        }
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setBio(userDTO.getBio());
        return userRepository.save(user);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
