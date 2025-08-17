package com.quora.quora.controller;

import com.quora.quora.DTO.QuestionDto;
import com.quora.quora.models.Question;
import com.quora.quora.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        Question createdQuestion = questionService.createQuestion(questionDto);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }
}
