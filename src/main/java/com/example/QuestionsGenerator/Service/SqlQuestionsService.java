package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.SqlQuestion;
import com.example.QuestionsGenerator.Repository.SqlQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlQuestionsService {

    @Autowired
    private SqlQuestionRepository sqlQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<SqlQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null || lastSentQuestionId == 0) {
            return sqlQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return sqlQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }


}
