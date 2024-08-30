package com.example.demo.Entity.doctor;

import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class DoctorEncryptId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eid;

    @Column(nullable = false,unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "did")
    private doctorDetails did;

    private int status;
}
