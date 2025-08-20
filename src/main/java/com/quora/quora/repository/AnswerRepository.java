package com.quora.quora.repository;

import com.quora.quora.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findByQuestionIdOrderByCreatedAtDesc(UUID questionId);

}
