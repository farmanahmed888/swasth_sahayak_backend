package com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCountDTO {
    private int doctor_id;
    private String doctor_name;
    private int open_slots;
    private String WorkingAddress;
    @JsonIgnore
    private int Blockcode;
    private String Specialization;

}
