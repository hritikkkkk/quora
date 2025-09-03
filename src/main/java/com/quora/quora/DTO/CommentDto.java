package com.quora.quora.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class CommentDto {
    @NotNull(message = "user id is required")
    private UUID user;
    @NotBlank(message = "Comment text is required")
    private String text;
}
