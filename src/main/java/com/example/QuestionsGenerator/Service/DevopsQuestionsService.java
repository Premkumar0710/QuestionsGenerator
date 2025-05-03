package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.DevopsQuestion;
import com.example.QuestionsGenerator.Repository.DevopsQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevopsQuestionsService {

    @Autowired
    private DevopsQuestionRepository devopsQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<DevopsQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null || lastSentQuestionId == 0) {
            return devopsQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return devopsQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }

}
