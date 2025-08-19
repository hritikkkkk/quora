package com.quora.quora.repository;

import com.quora.quora.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q ORDER BY q.createdAt DESC")
    List<Question> findAllOrderByCreatedAtDesc();

    @Query("SELECT q FROM Question q WHERE " +
            "(:text IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(q.body) LIKE LOWER(CONCAT('%', :text, '%'))) AND " +
            "(:tag IS NULL OR EXISTS (SELECT t FROM q.topics t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :tag, '%'))))")
    List<Question> searchQuestions(@Param("text") String text, @Param("tag") String tag);


}
