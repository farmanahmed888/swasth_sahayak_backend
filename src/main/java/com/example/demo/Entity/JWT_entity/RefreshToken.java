package com.example.demo.Entity.JWT_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshToken {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;


    private String token;
    private Instant expiryDate;
    @ManyToOne
    @JoinColumn(name="user_details",referencedColumnName = "username")
    private loginDetails logins;
}
