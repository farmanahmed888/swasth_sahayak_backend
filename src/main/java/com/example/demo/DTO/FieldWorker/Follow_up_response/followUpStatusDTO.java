package com.example.demo.DTO.FieldWorker.Follow_up_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class followUpStatusDTO {
    private int follow_up_id;
    private int fieldworker_id;
    private String patient_name;
    private String patient_address;
    private int patient_token;
    private String patient_abhaid;
    private boolean visited_status;
}
