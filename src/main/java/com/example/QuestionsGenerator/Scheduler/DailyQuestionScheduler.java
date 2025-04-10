package com.example.QuestionsGenerator.Scheduler;

import com.example.QuestionsGenerator.Entity.Question;
import com.example.QuestionsGenerator.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyQuestionScheduler {

    @Autowired
    private QuestionService questionService;

    private static final Long USER_ID = 1L;

    // Flag to prevent sending more than 2 questions in a day
    private boolean hasSentQuestionsForToday = false;

    // Flag to print log message only once per day
    private boolean hasPrintedLogForToday = false;

    // Runs every minute for testing purposes
    @Scheduled(cron = "0 * * * * ?")
    public void sendDailyQuestions() {
        if (hasSentQuestionsForToday) {
            if (!hasPrintedLogForToday) {
                System.out.println("Scheduler has already sent today's questions. Skipping this run.");
                hasPrintedLogForToday = true; // Mark the log as printed
            }
            return; // Skip if today's questions have already been sent
        }

        // Get next 2 questions for the user
        List<Question> questions = questionService.getNextQuestionsForUser(USER_ID);

        if (questions.isEmpty()) {
            System.out.println("No new questions to send today.");
            hasSentQuestionsForToday = true;  // Mark the day as complete
            hasPrintedLogForToday = true;  // Print log once for no questions
            return; // Exit if no questions are available
        }

        // Only send up to 2 questions
        int questionsToSend = Math.min(2, questions.size());

        StringBuilder body = new StringBuilder("Consistent hard work for a few hours every day can put you years ahead of others.\n 📌 Today's SQL/DSA Questions:\n\n");


        for (int i = 0; i < questionsToSend; i++) {
            Question q = questions.get(i);
            body.append("Q").append(i + 1).append(": ").append(q.getQuestionContent())
                    .append("\nDifficulty: ").append(q.getDifficulty()).append("\n\n");
        }

        String finalMessage = body.toString().trim();

        // Log the message to console (simulating the sending of the message)
        System.out.println(finalMessage);

        // Mark the flag as true, so no more questions will be sent for today
        hasSentQuestionsForToday = true;
    }

    // Reset flag at midnight every day
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetSchedulerFlag() {
        hasSentQuestionsForToday = false;
        hasPrintedLogForToday = false;  // Reset the print log flag for a new day
        System.out.println("Scheduler flag reset for the new day.");
    }
}
