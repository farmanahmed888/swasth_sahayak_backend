package com.example.demo.DTO.FieldWorker.PatientDashboard_FW_app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class patientDashboard_FWDTO {
    @JsonIgnore
    private int patient_id;
    private String patient_abhaid;
    private int fieldworker_id;
    private String patient_name;
    private String patient_address;
    private ongoingMedicationOrdersDTO ongoing_medication_orders;
    private List<top3prescriptionsDTO> recent_3_prescriptions;
    private boolean accessible;
    //private String doctor;
    /*
      String doctorName = null;
            patientDoctorMapping patientDoctorMapping = patientDoctorMappingRepository.findByAbhaid(patient.getAbhaid());
            if (patientDoctorMapping != null) {
                doctorDetails doctor = DoctorRepository.findByDid(patientDoctorMapping.getDoctorid());
                if (doctor != null) {
                    doctorName = doctor.getName();
                }
            }
            // Setting doctorName to patientProfileDTO
            patientProfileDTO.setDoctor(doctorName);






      },
    */

}
