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
}
