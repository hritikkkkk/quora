package com.quora.quora.service;

import com.quora.quora.DTO.QuestionDto;
import com.quora.quora.models.Question;
import com.quora.quora.models.User;
import com.quora.quora.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserService userService;

    public Question createQuestion(QuestionDto questionCreateDto) {
        User user = userService.getUserById(questionCreateDto.getUserId());

        Question question = Question.builder()
                .title(questionCreateDto.getTitle())
                .body(questionCreateDto.getBody())
                .user(user)
                .build();




        return questionRepository.save(question);
    }


}
