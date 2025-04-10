package com.example.QuestionsGenerator.Controller;

import com.example.QuestionsGenerator.Entity.Question;
import com.example.QuestionsGenerator.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // API to get next 2 questions for a user
    @GetMapping("/user/{userId}")
    public List<Question> getNextQuestions(@PathVariable Long userId) {
        return questionService.getNextQuestionsForUser(userId);
    }
}
