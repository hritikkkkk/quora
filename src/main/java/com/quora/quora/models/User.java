package com.quora.quora.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String bio;
}
