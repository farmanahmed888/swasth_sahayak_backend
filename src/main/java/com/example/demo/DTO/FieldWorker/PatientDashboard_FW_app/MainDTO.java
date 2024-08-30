package com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app;
import com.example.demo.DTO.FieldWorker.AvailableDoctorCountDTO.DoctorCountDTO;
import com.example.demo.DTO.FieldWorker.QuestionnaireDTO.AllQuestionForICD10;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
public class MainDTO {
    private sectorDetails field_worker_details;
    private List< PatientFollowUpDTO> follow_up;
    private List<patientDashboard_FWDTO> patient_details;
    private List<DoctorCountDTO> doctors;
    private List<AllQuestionForICD10>questionnaire;
}
