package com.example.demo.Entity.Questionnaire;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class patient_answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answer_id;

    @ManyToOne
    @JoinColumn(name="questionid")
    private Question_set ques_id;

    @ManyToOne
    @JoinColumn(name="diagnoseid")
    private diagnose_id_gen diagnoseid;


    @Column(nullable=false)
    private String answer;
}
