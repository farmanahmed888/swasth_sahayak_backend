package com.example.demo.DTO.Doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class doctordetailsDTO {
    private String name;
    private String specialization;
    private String DoctorId;
    private int blockCode;
}
