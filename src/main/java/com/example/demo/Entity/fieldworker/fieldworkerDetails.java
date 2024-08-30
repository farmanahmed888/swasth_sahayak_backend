package com.example.demo.Entity.fieldworker;
import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class fieldworkerDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int fid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    //@Convert(converter = Encrypt.class)
    private String mobileno;

    @Column(nullable = false)
    private String gender;



}
