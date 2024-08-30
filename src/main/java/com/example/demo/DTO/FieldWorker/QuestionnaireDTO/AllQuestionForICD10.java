package com.example.demo.DTO.FieldWorker.QuestionnaireDTO;

import lombok.Data;

import java.util.List;

@Data
public class AllQuestionForICD10 {
    private String icd10 ;
    private List<particularQuestion> questions;
}
