package com.quora.quora.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String bio;
}