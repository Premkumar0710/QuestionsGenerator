package com.example.QuestionsGenerator.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HldQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String questionContent;

    private String difficulty; // e.g., Easy, Medium, Hard

    private String tags; // e.g., Arrays, LinkedLists

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String answer;
}
