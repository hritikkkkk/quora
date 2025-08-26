package com.quora.quora.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDto {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Comment text is required")
    private String text;
}
