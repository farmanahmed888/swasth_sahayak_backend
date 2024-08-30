package com.example.demo.Entity.patient;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;


import java.sql.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="patient_details")
public class PatientDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int pid;

    @Column(nullable = false,unique = true)
    //@Convert(converter = Encrypt.class)
    private String abhaid;

    @Column(nullable = false)
    //@Convert(converter = Encrypt.class)
    private String name;

    //@Convert(converter = Encrypt.class)
    private String mobileno;

    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false)
    private String preferredlanguage;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    //@Convert(converter = Encrypt.class)
    private String Address;

    @Column(nullable = false)
    private int pincode;

    @Column(nullable = false)
    private int blockcode;



}
