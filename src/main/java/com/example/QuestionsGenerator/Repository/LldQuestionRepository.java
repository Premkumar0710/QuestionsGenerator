package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.HldQuestion;
import com.example.QuestionsGenerator.Entity.LldQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LldQuestionRepository extends JpaRepository<LldQuestion, Long> {

    List<LldQuestion> findFirst2ByOrderByIdAsc();
    List<LldQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);
}
