package com.quora.quora.controller;

import com.quora.quora.DTO.CommentDto;
import com.quora.quora.DTO.CommentResponseDto;
import com.quora.quora.models.Comment;
import com.quora.quora.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /** API: Add comment on Answer */
    @PostMapping("/answers/{answerId}/comments")
    public ResponseEntity<CommentResponseDto> addCommentOnAnswer(
            @PathVariable UUID answerId,
            @RequestBody @Valid CommentDto commentDto
    ) {
        Comment createdComment = commentService.addCommentOnAnswer(answerId, commentDto);
        return new ResponseEntity<>(commentService.toResponseDto(createdComment), HttpStatus.CREATED);
    }

    /** API: Add reply to a Comment */
    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<CommentResponseDto> addReplyToComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid CommentDto commentDto
    ) {
        Comment reply = commentService.addReplyToComment(commentId, commentDto);
        return new ResponseEntity<>(commentService.toResponseDto(reply), HttpStatus.CREATED);
    }
}
