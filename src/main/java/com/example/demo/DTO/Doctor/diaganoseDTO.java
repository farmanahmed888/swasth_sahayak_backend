package com.example.demo.DTO.Doctor;

import com.example.demo.Entity.patient.Artifacts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class diaganoseDTO {
    private String fieldworkername;
    private Date Date;
    private int artifacts;
    private  int diagnoseid;
}
