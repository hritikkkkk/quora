package com.quora.quora.controller;

import com.quora.quora.DTO.AnswerDto;
import com.quora.quora.models.Answer;
import com.quora.quora.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/{questionId}/answers")
    public ResponseEntity<Answer> createAnswer(
            @PathVariable UUID questionId,
            @Valid @RequestBody AnswerDto answerCreateDto) {
        Answer createdAnswer = answerService.createAnswer(questionId, answerCreateDto);
        return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
    }

    @GetMapping("/{questionId}/answers")
    public ResponseEntity<List<Answer>> getAnswersByQuestion(@PathVariable UUID questionId) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable UUID answerId) {
        Answer answer = answerService.getAnswerById(answerId);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/answers/{answerId}")
    public ResponseEntity<Answer> updateAnswer(
            @PathVariable UUID answerId,
            @Valid @RequestBody AnswerDto answerCreateDto) {
        Answer answer = answerService.updateAnswer(answerId, answerCreateDto);
        return ResponseEntity.ok(answer);
    }

    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID answerId){
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
}
