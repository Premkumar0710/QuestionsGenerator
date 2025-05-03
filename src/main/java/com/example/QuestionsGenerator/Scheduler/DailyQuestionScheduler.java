package com.example.QuestionsGenerator.Scheduler;

import com.example.QuestionsGenerator.Entity.SqlQuestion;
import com.example.QuestionsGenerator.Entity.DsaQuestion;
import com.example.QuestionsGenerator.Entity.LldQuestion;
import com.example.QuestionsGenerator.Entity.HldQuestion;
import com.example.QuestionsGenerator.Entity.JavaAndSbQuestion;
import com.example.QuestionsGenerator.Entity.DevopsQuestion;
import com.example.QuestionsGenerator.Notification.SendNotification;
import com.example.QuestionsGenerator.Notification.NotificationType;
import com.example.QuestionsGenerator.Notification.NotificationService;
import com.example.QuestionsGenerator.Service.SqlQuestionsService;
import com.example.QuestionsGenerator.Service.DsaQuestionsService;
import com.example.QuestionsGenerator.Service.LldQuestionsService;
import com.example.QuestionsGenerator.Service.HldQuestionsService;
import com.example.QuestionsGenerator.Service.JavaAndSbQuestionsService;
import com.example.QuestionsGenerator.Service.DevopsQuestionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyQuestionScheduler {

    @Autowired
    private SqlQuestionsService sqlQuestionService;

    @Autowired
    private DsaQuestionsService dsaQuestionService;

    @Autowired
    private LldQuestionsService lldQuestionService;

    @Autowired
    private HldQuestionsService hldQuestionService;

    @Autowired
    private JavaAndSbQuestionsService javaAndSbQuestionService;

    @Autowired
    private DevopsQuestionsService devopsQuestionService;

    @Autowired
    private NotificationService notificationService;

    private static final Long USER_ID = 1L;
    private boolean hasSentQuestionsForToday = false;
    private boolean hasPrintedLogForToday = false;

    // Global variable to specify the type of questions to send (e.g., "DSA", "SQL", etc.)
    private String selectedQuestionType = "DSA"; // Set this to the desired category

    @Scheduled(cron = "0 44 22 * * ?")
    public void sendDailyQuestions() {
        if (hasSentQuestionsForToday) {
            if (!hasPrintedLogForToday) {
                System.out.println("Scheduler has already sent today's questions. Skipping this run.");
                hasPrintedLogForToday = true;
            }
            return;
        }

        // Fetch questions for each category
        List<SqlQuestion> sqlQuestions = sqlQuestionService.getNextQuestions(USER_ID);
        List<DsaQuestion> dsaQuestions = dsaQuestionService.getNextQuestions(USER_ID);
        List<LldQuestion> lldQuestions = lldQuestionService.getNextQuestions(USER_ID);
        List<HldQuestion> hldQuestions = hldQuestionService.getNextQuestions(USER_ID);
        List<JavaAndSbQuestion> javaAndSbQuestions = javaAndSbQuestionService.getNextQuestions(USER_ID);
        List<DevopsQuestion> devopsQuestions = devopsQuestionService.getNextQuestions(USER_ID);

        if (sqlQuestions.isEmpty() && dsaQuestions.isEmpty() && lldQuestions.isEmpty() && hldQuestions.isEmpty()
                && javaAndSbQuestions.isEmpty() && devopsQuestions.isEmpty()) {
            System.out.println("No new questions to send today.");
            hasSentQuestionsForToday = true;
            hasPrintedLogForToday = true;
            return;
        }

        int questionsToSend = 2; // Sending 2 questions per category
        StringBuilder body = new StringBuilder("Consistent hard work for a few hours every day can put you years ahead of others.\n 📌 Today's Questions:\n\n");

        // Add logic to pick questions based on the selected category
        if ("SQL".equals(selectedQuestionType)) {
            addQuestionsToMessage(sqlQuestions, body, questionsToSend, "SQL");
        } else if ("DSA".equals(selectedQuestionType)) {
            addQuestionsToMessage(dsaQuestions, body, questionsToSend, "DSA");
        } else if ("LLD".equals(selectedQuestionType)) {
            addQuestionsToMessage(lldQuestions, body, questionsToSend, "LLD");
        } else if ("HLD".equals(selectedQuestionType)) {
            addQuestionsToMessage(hldQuestions, body, questionsToSend, "HLD");
        } else if ("Java & SB".equals(selectedQuestionType)) {
            addQuestionsToMessage(javaAndSbQuestions, body, questionsToSend, "Java & SB");
        } else if ("DevOps".equals(selectedQuestionType)) {
            addQuestionsToMessage(devopsQuestions, body, questionsToSend, "DevOps");
        } else {
            System.out.println("Invalid question type specified. Skipping question selection.");
            return;
        }

        String finalMessage = body.toString().trim();

        // Send notification via WhatsApp (or other notification types based on annotation or logic)
        notificationService.sendNotification(finalMessage, NotificationType.WHATSAPP);

        hasSentQuestionsForToday = true;
    }

    private void addQuestionsToMessage(List<?> questions, StringBuilder body, int questionsToSend, String category) {
        for (int i = 0; i < Math.min(questionsToSend, questions.size()); i++) {
            Object q = questions.get(i);
            body.append("Category: ").append(category).append("\n");

            // For SQL Questions
            if (q instanceof SqlQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((SqlQuestion) q).getQuestionContent())  // Using getQuestionContent for SQL
                        .append("\nDifficulty: ").append(((SqlQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }

            // For DSA Questions
            else if (q instanceof DsaQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((DsaQuestion) q).getQuestionContent())  // Using getQuestionContent for DSA
                        .append("\nDifficulty: ").append(((DsaQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }

            // For LLD Questions
            else if (q instanceof LldQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((LldQuestion) q).getQuestionContent())  // Using getQuestionContent for LLD
                        .append("\nDifficulty: ").append(((LldQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }

            // For HLD Questions
            else if (q instanceof HldQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((HldQuestion) q).getQuestionContent())  // Using getQuestionContent for HLD
                        .append("\nDifficulty: ").append(((HldQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }

            // For Java & SB Questions
            else if (q instanceof JavaAndSbQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((JavaAndSbQuestion) q).getQuestionContent())  // Using getQuestionContent for Java & SB
                        .append("\nDifficulty: ").append(((JavaAndSbQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }

            // For DevOps Questions
            else if (q instanceof DevopsQuestion) {
                body.append("Q").append(i + 1).append(": ").append(((DevopsQuestion) q).getQuestionContent())  // Using getQuestionContent for DevOps
                        .append("\nDifficulty: ").append(((DevopsQuestion) q).getDifficulty())  // Adjust as per available field
                        .append("\n\n");
            }
        }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void resetSchedulerFlag() {
        hasSentQuestionsForToday = false;
        hasPrintedLogForToday = false;
        System.out.println("Scheduler flag reset for the new day.");
    }
}

