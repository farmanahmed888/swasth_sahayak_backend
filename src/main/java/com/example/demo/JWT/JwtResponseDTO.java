package com.example.demo.JWT;

import com.example.demo.Entity.JWT_entity.RefreshToken;
import com.example.demo.Entity.JWT_entity.RoleType;
import lombok.Data;

@Data
public class JwtResponseDTO {
    private String username;
    private String name;
    private RoleType role;
    private String jwtToken;
    private RefreshToken refreshToken;
}
