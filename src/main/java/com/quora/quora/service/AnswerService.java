package com.quora.quora.service;

import com.quora.quora.DTO.AnswerDto;
import com.quora.quora.exception.ResourceNotFoundException;
import com.quora.quora.models.Answer;
import com.quora.quora.models.Question;
import com.quora.quora.models.User;
import com.quora.quora.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final QuestionService questionService;

    public Answer createAnswer(UUID questionId, AnswerDto answerCreateDto) {
        User user = userService.getUserById(answerCreateDto.getUserId());
        Question question = questionService.getQuestionById(questionId);

        Answer answer = Answer.builder()
                .text(answerCreateDto.getText())
                .user(user)
                .question(question)
                .build();

        return answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(UUID questionId) {
        return answerRepository.findByQuestionIdOrderByCreatedAtDesc(questionId);
    }

    public Answer getAnswerById(UUID answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + answerId));
    }

    public Answer updateAnswer(UUID questionId, AnswerDto answerUpdateDto) {
        Answer existingAnswer = getAnswerById(questionId);
        if (answerUpdateDto.getText() != null) {
            existingAnswer.setText(answerUpdateDto.getText());
        }


        return answerRepository.save(existingAnswer);
    }

    public void deleteAnswer(UUID answerId) {
        Answer answer = getAnswerById(answerId);
        answerRepository.delete(answer);

    }


}
