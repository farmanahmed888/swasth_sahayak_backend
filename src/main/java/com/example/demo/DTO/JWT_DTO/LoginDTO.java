package com.example.demo.DTO.JWT_DTO;

import com.example.demo.Entity.JWT_entity.RoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
public class LoginDTO {
    private String username;
    private String name;
    private RoleType role;
}
