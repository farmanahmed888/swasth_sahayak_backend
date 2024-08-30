package com.example.demo.Entity.Questionnaire;

import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Question_set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @OneToMany(mappedBy = "ques_id")
    private List<patient_answers> qIds;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String quesText;

    //default value is 0 for active
    private int status;

    private String type; // text, mcq and nat

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "icd10")
    private ICD10_mapping icd10;

    @OneToMany(mappedBy = "questionid",cascade = CascadeType.ALL)
    private List<optiontabeforMCQ> optiontabeforMCQList;
}
