package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.HldQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HldQuestionRepository extends JpaRepository<HldQuestion, Long> {

    List<HldQuestion> findFirst2ByOrderByIdAsc();
    List<HldQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);

}
