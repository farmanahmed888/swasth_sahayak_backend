package com.example.demo.DTO.FieldWorker.Follow_up_response;

import lombok.Data;

import java.sql.Date;

@Data
public class registeredPatientDTO {
    private int fieldworker_id;
    private String patient_abhaid;
    private String patient_address;
    private int patient_blockCode;
    private Date patient_dob;
    private String patient_gender;
    private String patient_name;
    private String patient_phoneNumber;
    private int patient_pincode;
    private String patient_preferred_language;

}

