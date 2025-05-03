package com.example.QuestionsGenerator.Repository;

import com.example.QuestionsGenerator.Entity.DevopsQuestion;
import com.example.QuestionsGenerator.Entity.HldQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevopsQuestionRepository extends JpaRepository<DevopsQuestion, Long> {

    List<DevopsQuestion> findFirst2ByOrderByIdAsc();
    List<DevopsQuestion> findFirst2ByIdGreaterThanOrderByIdAsc(Long id);
}
