package com.example.demo.Entity.supervisor;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class supervisor_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @Column(nullable = false)
    //@Convert(converter = Encrypt.class)
    private String name;

    @Column(nullable = false,unique = true)
    //@Convert(converter = Encrypt.class)
    private String mobileno;

    @Column(nullable = false)
    private String gender;

}
