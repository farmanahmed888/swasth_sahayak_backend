package com.example.demo.DTO.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientdetailDTO {
    private String name;
    private String mobileno;
    private int pincode;
    private String address;
    private String feildworkername;
    private String did;
    private int prescriptionid;
    private String prescription;
    private String doctor_comment;
    //private diagnosis_id;
    //private int artifact_id;
}
