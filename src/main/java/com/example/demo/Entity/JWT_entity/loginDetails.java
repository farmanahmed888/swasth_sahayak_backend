package com.example.demo.Entity.JWT_entity;

import com.example.demo.EncDec.Encrypt;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class loginDetails {
    @Id
    private String username;
    //@Convert(converter = Encrypt.class)
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    @OneToMany(mappedBy = "token")
    private List<RefreshToken> tokens;

    @OneToOne(mappedBy = "logindetails")
    private forgotpassword forgotpassword;

}
