package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.DsaQuestion;
import com.example.QuestionsGenerator.Entity.HldQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DsaQuestionRepository extends JpaRepository<DsaQuestion, Long> {

    List<DsaQuestion> findFirst2ByOrderByIdAsc();
    List<DsaQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);
}
