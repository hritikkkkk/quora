package com.quora.quora.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Data
public class AnswerDto {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Answer text is required")
    private String text;
}