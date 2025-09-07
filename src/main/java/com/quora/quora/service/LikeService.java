package com.quora.quora.service;

import com.quora.quora.exception.DuplicateResourceException;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.Like;
import com.quora.quora.models.User;
import com.quora.quora.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    public void likeContent(String type, UUID targetId, UUID userId) {
        Like.TargetType targetType = parseTargetType(type);

        verifyTargetExists(targetType, targetId);

        if (likeRepository.existsByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)) {
            throw new DuplicateResourceException("User has already liked this " + type);
        }

        User user = userService.getUserById(userId);
        Like like = Like.builder().user(user).targetId(targetId).targetType(targetType).build();

        likeRepository.save(like);
    }

    private void verifyTargetExists(Like.TargetType targetType, UUID targetId) {
        switch (targetType) {
            case COMMENT -> commentService.getCommentById(targetId);
            case ANSWER -> answerService.getAnswerById(targetId);
            case QUESTION -> questionService.getQuestionById(targetId);
        }
    }

    private Like.TargetType parseTargetType(String type) {
        return switch (type.toLowerCase()) {
            case "questions" -> Like.TargetType.QUESTION;
            case "answers" -> Like.TargetType.ANSWER;
            case "comments" -> Like.TargetType.COMMENT;
            default -> throw new IllegalArgumentException("Invalid target type: " + type);
        };
    }

    public void unlikeContent(String type, UUID targetId, UUID userId) {
        Like.TargetType targetType = parseTargetType(type);

        Optional<Like> existingLike = likeRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);

        if (existingLike.isEmpty()) {
            throw new ResourceNotFoundException("Like not found for user " + userId + " on " + type + " " + targetId);
        }

        likeRepository.delete(existingLike.get());
    }

    public long getLikeCount(String type, UUID targetId) {
        Like.TargetType targetType = parseTargetType(type);
        return likeRepository.countByTargetIdAndTargetType(targetId, targetType);
    }
}
