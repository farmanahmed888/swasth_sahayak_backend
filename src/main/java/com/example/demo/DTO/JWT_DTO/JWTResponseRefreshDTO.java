package com.example.demo.DTO.JWT_DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class JWTResponseRefreshDTO {
    private String accesstoken;
    private String refreshtoken;

}
