package com.example.demo.Entity.fieldworker;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class fieldWorkerIdEncrypt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feid;

    @Column(nullable = false,unique = true)
    private String fieldworkerid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fid")
    private fieldworkerDetails fid;
    //default value is 0 for active
    private int status;

}
