package com.quora.quora.service;

import com.quora.quora.DTO.UserDTO;
import com.quora.quora.exception.DuplicateResourceException;
import com.quora.quora.models.User;
import com.quora.quora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
