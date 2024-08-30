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
public class recentlytreatedDTO {
    private String patientname;
    private Date followupdate;
    private int prescriptionid;
    private String disease;

}
