package com.example.QuestionsGenerator.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String questionContent;

    private String difficulty; // e.g., Easy, Medium, Hard

    private String qnType; // e.g., "SQL", "DSA"
}
