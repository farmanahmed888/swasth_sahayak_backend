package com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class top3prescriptionsDTO {

    private String disease_name;
    private String prescription;
    private Date date;
    private String doctor_name;


}
