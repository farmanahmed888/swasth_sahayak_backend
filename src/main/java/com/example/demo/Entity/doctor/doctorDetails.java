package com.example.demo.Entity.doctor;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class doctorDetails {

//    @GeneratedValue(generator = "doctorIdGen",strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "doctorIdGen",sequenceName = "doctor_seq",initialValue = 50,allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int did;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    //@Convert(converter = Encrypt.class)
    private String mobileno;

    @Column(nullable = false)
    private Date dateofjoining;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    //@Convert(converter = Encrypt.class)
    private String workingaddress;

    @Column(nullable = false)
    private int pincode;

    @Column(nullable = false)
    private int blockcode;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private int countofpatient;

}
