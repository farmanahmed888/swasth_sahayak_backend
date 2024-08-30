package com.example.demo.DTO.FieldWorker.Follow_up_response;

import lombok.Data;
import java.util.List;
@Data
public class ResponseDTO {
    private String fieldworker_id;
    private List<followUpStatusDTO> follow_up;
    private List<questionnaireResponseDTO> questionnaire_response;
    private List<fieldWorkerCommentDTO> fieldworker_comments;
    private List<artifactsDTO> artifacts;
    private List<choosenDoctorDTO> chosen_doctor;
    private List<registeredPatientDTO> patient_registeration;

}
