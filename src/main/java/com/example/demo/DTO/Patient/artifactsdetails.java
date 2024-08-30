package com.example.demo.DTO.Patient;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class artifactsdetails {
    private String image;
}
