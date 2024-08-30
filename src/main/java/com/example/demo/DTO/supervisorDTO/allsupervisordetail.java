package com.example.demo.DTO.supervisorDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class allsupervisordetail {
    private String supervisorid;

    private String name;

    private String mobileno;

    private String gender;

}
