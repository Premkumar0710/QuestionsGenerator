package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.HldQuestion;
import com.example.QuestionsGenerator.Entity.SqlQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlQuestionRepository extends JpaRepository<SqlQuestion, Long> {

    List<SqlQuestion> findFirst2ByOrderByIdAsc();
    List<SqlQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);
}
