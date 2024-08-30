package com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ongoingMedicationOrdersDTO {
    private String icd10_code;
    private String doctor_name;
    private String doctor_comment;
    private Date Date;
    private String questionnaire_type;

}
