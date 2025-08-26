package com.quora.quora.controller;

import com.quora.quora.DTO.CommentDto;
import com.quora.quora.models.Comment;
import com.quora.quora.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
