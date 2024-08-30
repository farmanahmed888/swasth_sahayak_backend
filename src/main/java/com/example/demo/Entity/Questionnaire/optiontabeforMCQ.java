package com.example.demo.Entity.Questionnaire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class optiontabeforMCQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionid;

    private String options;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionid")
    private Question_set questionid;
}
