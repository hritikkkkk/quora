package com.quora.quora.service;

import com.quora.quora.DTO.CommentDto;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.Answer;
import com.quora.quora.models.Comment;
import com.quora.quora.models.User;
import com.quora.quora.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public Comment createCommentOnAnswer(UUID answerId, CommentDto commentDto) {
        User user = userService.getUserById(commentDto.getUserId());

        Answer answer = answerService.getAnswerById(answerId);

        Comment comment = Comment.builder()
                .parentId(answerId)
                .parentType(Comment.ParentType.ANSWER)
                .text(commentDto.getText())
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

}
