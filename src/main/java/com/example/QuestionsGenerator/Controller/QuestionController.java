package com.example.QuestionsGenerator.Controller;

import com.example.QuestionsGenerator.Entity.*;
import com.example.QuestionsGenerator.Repository.UserRepository;
import com.example.QuestionsGenerator.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private SqlQuestionsService sqlQuestionsService;

    @Autowired
    private DsaQuestionsService dsaQuestionsService;

    @Autowired
    private LldQuestionsService lldQuestionsService;

    @Autowired
    private HldQuestionsService hldQuestionsService;

    @Autowired
    private JavaAndSbQuestionsService javaAndSbQuestionsService;

    @Autowired
    private DevopsQuestionsService devopsQuestionsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send/{userId}")
    public String sendQuestions(@PathVariable Long userId, @RequestParam String currentQuestionType) {
        User user = getUserById(userId);
        if (user == null) {
            return "User not found!";
        }

        List<?> questions;

        switch (currentQuestionType.toUpperCase()) {
            case "SQL":
                questions = sqlQuestionsService.getNextQuestions(user.getLastSentSqlQuestionId());
                break;
            case "DSA":
                questions = dsaQuestionsService.getNextQuestions(user.getLastSentDsaQuestionId());
                break;
            case "LLD":
                questions = lldQuestionsService.getNextQuestions(user.getLastSentLldQuestionId());
                break;
            case "HLD":
                questions = hldQuestionsService.getNextQuestions(user.getLastSentHldQuestionId());
                break;
            case "JAVA":
                questions = javaAndSbQuestionsService.getNextQuestions(user.getLastSentJavaQuestionId());
                break;
            case "DEVOPS":
                questions = devopsQuestionsService.getNextQuestions(user.getLastSentDevopsQuestionId());
                break;
            default:
                return "Invalid question type!";
        }

        if (questions.isEmpty()) {
            return "No more questions available!";
        }

        // Simulate sending the questions (for now, printing)
        StringBuilder message = new StringBuilder("Today's Questions:\n");
        for (Object question : questions) {
            if (question instanceof SqlQuestion sqlQn) {
                message.append("Q: ").append(sqlQn.getQuestionContent()).append("\nA: ").append(sqlQn.getAnswer()).append("\n\n");
            } else if (question instanceof DsaQuestion dsaQn) {
                message.append("Q: ").append(dsaQn.getQuestionContent()).append("\nA: ").append(dsaQn.getAnswer()).append("\n\n");
            } else if (question instanceof LldQuestion lldQn) {
                message.append("Q: ").append(lldQn.getQuestionContent()).append("\nA: ").append(lldQn.getAnswer()).append("\n\n");
            } else if (question instanceof HldQuestion hldQn) {
                message.append("Q: ").append(hldQn.getQuestionContent()).append("\nA: ").append(hldQn.getAnswer()).append("\n\n");
            } else if (question instanceof JavaAndSbQuestion javaQn) {
                message.append("Q: ").append(javaQn.getQuestionContent()).append("\nA: ").append(javaQn.getAnswer()).append("\n\n");
            } else if (question instanceof DevopsQuestion devopsQn) {
                message.append("Q: ").append(devopsQn.getQuestionContent()).append("\nA: ").append(devopsQn.getAnswer()).append("\n\n");
            }
        }

        // Now Update user's last sent question ID properly
        updateLastSentQuestionId(user, currentQuestionType, questions);

        return message.toString();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private void updateLastSentQuestionId(User user, String currentQuestionType, List<?> questions) {
        Object lastQuestion = questions.get(questions.size() - 1);
        Long lastQuestionId = null;

        if (currentQuestionType.equalsIgnoreCase("SQL") && lastQuestion instanceof SqlQuestion sqlQn) {
            lastQuestionId = sqlQn.getId();
            user.setLastSentSqlQuestionId(lastQuestionId);
        } else if (currentQuestionType.equalsIgnoreCase("DSA") && lastQuestion instanceof DsaQuestion dsaQn) {
            lastQuestionId = dsaQn.getId();
            user.setLastSentDsaQuestionId(lastQuestionId);
        } else if (currentQuestionType.equalsIgnoreCase("LLD") && lastQuestion instanceof LldQuestion lldQn) {
            lastQuestionId = lldQn.getId();
            user.setLastSentLldQuestionId(lastQuestionId);
        } else if (currentQuestionType.equalsIgnoreCase("HLD") && lastQuestion instanceof HldQuestion hldQn) {
            lastQuestionId = hldQn.getId();
            user.setLastSentHldQuestionId(lastQuestionId);
        } else if (currentQuestionType.equalsIgnoreCase("JAVA") && lastQuestion instanceof JavaAndSbQuestion javaQn) {
            lastQuestionId = javaQn.getId();
            user.setLastSentJavaQuestionId(lastQuestionId);
        } else if (currentQuestionType.equalsIgnoreCase("DEVOPS") && lastQuestion instanceof DevopsQuestion devopsQn) {
            lastQuestionId = devopsQn.getId();
            user.setLastSentDevopsQuestionId(lastQuestionId);
        }

        if (lastQuestionId != null) {
            userRepository.save(user);
        }
    }
}
