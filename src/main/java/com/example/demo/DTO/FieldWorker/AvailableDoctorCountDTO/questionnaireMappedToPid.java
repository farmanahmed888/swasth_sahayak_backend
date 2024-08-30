package com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO;

import com.example.demo.DTO.FieldWorker.Follow_up_response.questionnaireResponseDTO;
import lombok.Data;

@Data
public class questionnaireMappedToPid {
    private int pid;
   private questionnaireResponseDTO ques;
}
