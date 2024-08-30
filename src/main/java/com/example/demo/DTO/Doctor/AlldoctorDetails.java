package com.example.demo.DTO.Doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlldoctorDetails {
    private String name;
    private String specialization;
    private String DoctorId;
    private int blockCode;
    private String mobileno;
    private Date dateofjoining;
    private String gender;
    private String workingaddress;
    private int pinecode;
    private int countofpatient;
    private int status;

}
