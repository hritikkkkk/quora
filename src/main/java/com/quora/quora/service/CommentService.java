package com.quora.quora.service;

import com.quora.quora.DTO.CommentDto;
import com.quora.quora.DTO.CommentResponseDto;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.Answer;
import com.quora.quora.models.Comment;
import com.quora.quora.models.User;
import com.quora.quora.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AnswerService answerService;

    /**
     * Comment on an Answer
     */
    public Comment addCommentOnAnswer(UUID answerId, CommentDto commentDto) {
        User user = userService.getUserById(commentDto.getUser());
        Answer answer = answerService.getAnswerById(answerId);

        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .user(user)
                .answer(answer)
                .build();

        return commentRepository.save(comment);
    }

    /**
     * Reply to another Comment
     */
    public Comment addReplyToComment(UUID parentCommentId, CommentDto commentDto) {
        User user = userService.getUserById(commentDto.getUser());
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent comment not found"));

        Comment reply = Comment.builder()
                .text(commentDto.getText())
                .user(user)
                .parentComment(parentComment)
                .answer(parentComment.getAnswer()) // keep same answer reference
                .build();

        return commentRepository.save(reply);
    }

    /**
     * Convert Comment → Response DTO (recursive for replies)
     */
    public CommentResponseDto toResponseDto(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userId(comment.getUser().getId())
                .answerId(comment.getAnswer() != null ? comment.getAnswer().getId() : null)
                .createdAt(comment.getCreatedAt())
                .replies(comment.getReplies() != null
                        ? comment.getReplies().stream()
                        .map(this::toResponseDto) // recursion
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public Comment getCommentById(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
    }
}
