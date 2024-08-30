package com.example.demo.Entity.JWT_entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class forgotpassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int fpid;

    @Column(nullable = false)
    private Integer otp;
    @Column(nullable = false)
    private Date expirationTine;

    @OneToOne
    @JoinColumn(name = "username")
    private loginDetails logindetails;
}

