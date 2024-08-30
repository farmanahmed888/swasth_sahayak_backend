package com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientFollowUpDTO {
    @JsonIgnore
    private int patient_id;
    private int follow_up_id;
    private String patient_name;
    private String patient_token;
    private String patient_abhaid;
    private String patient_address;
    private String ICD10;
    private Boolean visited_status;
    private Date patient_followupdate;
}
