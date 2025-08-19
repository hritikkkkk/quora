package com.quora.quora.controller;

import com.quora.quora.DTO.QuestionDto;
import com.quora.quora.models.Question;
import com.quora.quora.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = questionService.getQuestions();
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        Question createdQuestion = questionService.createQuestion(questionDto);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestions(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) String tag) {
        List<Question> questions = questionService.searchQuestions(text, tag);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable UUID questionId) {
        Question question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable UUID questionId,
            @RequestBody Question questionUpdate) {
        Question updatedQuestion = questionService.updateQuestion(questionId, questionUpdate);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
