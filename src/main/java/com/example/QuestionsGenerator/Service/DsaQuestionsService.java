package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.DsaQuestion;
import com.example.QuestionsGenerator.Repository.DsaQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DsaQuestionsService {

    @Autowired
    private DsaQuestionRepository dsaQuestionRepository;

    // Method to get the next set of 2 questions after the last sent question
    public List<DsaQuestion> getNextQuestions(Long lastSentQuestionId) {
        if (lastSentQuestionId == null || lastSentQuestionId == 0) {
            return dsaQuestionRepository.findFirst2ByOrderByIdAsc();
        } else {
            return dsaQuestionRepository.findFirst2ByIdGreaterThanOrderByIdAsc(lastSentQuestionId);
        }
    }

}
