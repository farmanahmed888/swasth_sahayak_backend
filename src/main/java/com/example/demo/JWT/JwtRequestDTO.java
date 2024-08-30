package com.example.demo.JWT;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class JwtRequestDTO {
    private String username;
    private String password;
    private String role;
}
