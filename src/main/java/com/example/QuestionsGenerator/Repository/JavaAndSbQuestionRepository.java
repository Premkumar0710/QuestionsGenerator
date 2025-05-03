package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.HldQuestion;
import com.example.QuestionsGenerator.Entity.JavaAndSbQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JavaAndSbQuestionRepository extends JpaRepository<JavaAndSbQuestion, Long> {

    List<JavaAndSbQuestion> findFirst2ByOrderByIdAsc();
    List<JavaAndSbQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);
}
