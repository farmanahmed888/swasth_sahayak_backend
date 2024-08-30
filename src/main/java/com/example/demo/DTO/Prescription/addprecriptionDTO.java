package com.example.demo.DTO.Prescription;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class  addprecriptionDTO {
    private int pid;

    private Date prescriptiondate;

    private String prescription;


    private String doctorcomment;


    private String diseasename;


    private String doctorid;

    private List<Date> followUpDate;

    private int diagnosisid;
}
