package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.JavaAndSbQuestion;
import com.example.QuestionsGenerator.Repository.JavaAndSbQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JavaAndSbQuestionsService {

    @Autowired
    private JavaAndSbQuestionRepository javaAndSbQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<JavaAndSbQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null || lastSentQuestionId == 0) {
            return javaAndSbQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return javaAndSbQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }

}
