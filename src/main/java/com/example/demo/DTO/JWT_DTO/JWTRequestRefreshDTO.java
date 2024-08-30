package com.example.demo.DTO.JWT_DTO;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Data

public class JWTRequestRefreshDTO {
    private String token;
    
}
