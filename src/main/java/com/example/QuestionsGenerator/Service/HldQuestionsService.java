package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.HldQuestion;
import com.example.QuestionsGenerator.Repository.HldQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HldQuestionsService {

    @Autowired
    private HldQuestionRepository hldQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<HldQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null) {
            return hldQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return hldQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }

}
