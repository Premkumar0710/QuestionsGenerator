package com.example.QuestionsGenerator.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private LocalDate subscriptionDate;

    private Long lastSentSqlQuestionId; // Track SQL question ID

    private Long lastSentDsaQuestionId; // Track DSA question ID

    private Long lastSentLldQuestionId; // Track LLD question ID

    private Long lastSentHldQuestionId; // Track HLD question ID

    private Long lastSentJavaQuestionId; // Track Java question ID

    private Long lastSentDevopsQuestionId; // Track DevOps question ID

    // Update last sent question for each category
    public void updateLastSentQuestionId(String questionType, Long questionId) {
        switch (questionType.toUpperCase()) {
            case "SQL":
                this.lastSentSqlQuestionId = questionId;
                break;
            case "DSA":
                this.lastSentDsaQuestionId = questionId;
                break;
            case "LLD":
                this.lastSentLldQuestionId = questionId;
                break;
            case "HLD":
                this.lastSentHldQuestionId = questionId;
                break;
            case "JAVA":
                this.lastSentJavaQuestionId = questionId;
                break;
            case "DEVOPS":
                this.lastSentDevopsQuestionId = questionId;
                break;
            default:
                throw new IllegalArgumentException("Invalid question type");
        }
    }
}
