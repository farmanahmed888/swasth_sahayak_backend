package com.example.demo.DTO.FieldWorker.QuestionnaireDTO;

import lombok.Data;

import java.util.List;

@Data
public class particularQuestion {
    private String ques_type;
    private  int question_id;
    private String question_text;
    private List<String> option;
}
