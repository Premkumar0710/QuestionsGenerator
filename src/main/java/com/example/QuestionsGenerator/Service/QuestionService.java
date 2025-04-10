package com.example.QuestionsGenerator.Service;

import com.example.QuestionsGenerator.Entity.Question;
import com.example.QuestionsGenerator.Entity.User;
import com.example.QuestionsGenerator.Repository.QuestionRepository;
import com.example.QuestionsGenerator.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Question> getQuestionsForDay(int day) {
        int startIndex = (day - 1) * 2;
        return questionRepository.findAll().stream()
                .skip(startIndex)
                .limit(2)
                .toList();
    }

    public List<Question> getNextQuestionsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long lastSentId = user.getLastSentQuestionId();
        List<Question> nextQuestions = questionRepository
                .findTop2ByIdGreaterThanOrderByIdAsc(lastSentId == null ? 0 : lastSentId);

        if (!nextQuestions.isEmpty()) {
            Long newLastSentId = nextQuestions.get(nextQuestions.size() - 1).getId();
            user.setLastSentQuestionId(newLastSentId);
            userRepository.save(user);
        }

        return nextQuestions;
    }
}
