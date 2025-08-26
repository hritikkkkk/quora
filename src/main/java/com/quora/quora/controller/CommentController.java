package com.quora.quora.controller;

import com.quora.quora.DTO.CommentDto;
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

    @PostMapping("/answers/{answerId}/comments")
    public ResponseEntity<Comment> createCommentOnAnswer(
            @PathVariable UUID answerId,
            @Valid @RequestBody CommentDto commentDto) {
        Comment createdComment = commentService.createCommentOnAnswer(answerId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PostMapping("/comments/{commentId}/comments")
    public ResponseEntity<Comment> createCommentOnComment(
            @PathVariable UUID commentId,
            @Valid @RequestBody CommentDto commentDto) {
        Comment createdComment = commentService.createCommentOnComment(commentId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

}
