package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.LldQuestion;
import com.example.QuestionsGenerator.Repository.LldQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LldQuestionsService {

    @Autowired
    private LldQuestionRepository lldQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<LldQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null || lastSentQuestionId == 0) {
            return lldQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return lldQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }

}
