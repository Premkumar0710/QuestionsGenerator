package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Find questions by type (e.g., SQL, DSA)
    List<Question> findByQnType(String qnType);

    // Find questions by difficulty level (e.g., Easy, Medium, Hard)
    List<Question> findByDifficulty(String difficulty);

    // Find questions by type and sort by ID ascending (can help in picking sequentially)
    List<Question> findByQnTypeOrderByIdAsc(String qnType);

    List<Question> findTop2ByIdGreaterThanOrderByIdAsc(Long id);

    @Query("SELECT q FROM Question q WHERE q.id > :lastId ORDER BY q.id ASC")
    List<Question> findNextQuestions(@Param("lastId") Long lastId, Pageable pageable);


}
