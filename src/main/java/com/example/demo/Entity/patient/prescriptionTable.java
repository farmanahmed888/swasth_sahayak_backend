package com.example.demo.Entity.patient;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="prescription_Table")
public class prescriptionTable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int prescriptionid;

    @Column(nullable = false)
    private int pid;

    @Column(nullable = false)
    private Date prescriptiondate;

    @Column(nullable = false,length = 5000)
    private String prescription;


    @Column(nullable = false,length = 1000)
    private String doctorcomment;


    @Column(nullable = false)
    private String diseasename;


    @Column(nullable = false)
    private String doctorid;


    @Column(nullable = false)
    private int diagnosisid;

    //follow_up Id
    //follow-up status



}
