package com.example.demo.DTO.Questionnare;

import com.example.demo.Entity.Questionnaire.patient_answers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class getdiagnosedetail {
    private String fieldworkercomment;
    private List<questionanswerDTO> patientanswers;
    private Date date;
    private String icd10;
    private int diagnoseid;
    private  int pid;
    private Date diagonsedate;
}
