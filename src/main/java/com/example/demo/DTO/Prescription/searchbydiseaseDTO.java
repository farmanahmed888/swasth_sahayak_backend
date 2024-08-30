package com.example.demo.DTO.Prescription;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class searchbydiseaseDTO {
    private String patientname;
    private String abhaid;
    private Date prescriptiondate;
    private String diseasename;
}
