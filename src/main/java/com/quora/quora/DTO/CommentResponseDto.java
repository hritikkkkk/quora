package com.quora.quora.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CommentResponseDto {
    private UUID id;
    private String text;
    private UUID userId;
    private UUID answerId;
    private List<CommentResponseDto> replies;
    private LocalDateTime createdAt;
}
