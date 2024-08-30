package com.example.demo.Entity.FollowUp;

import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import com.example.demo.Entity.common_entities.ICD10_mapping;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.patient.PatientDetails;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class followUpData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int followupid;
    @ManyToOne
    @JoinColumn(name = "fieldworkerid")
    private fieldworkerDetails fieldworkerid;

    @ManyToOne
    @JoinColumn(name="patientid")
    private PatientDetails patientid;

    @ManyToOne
    @JoinColumn(name = "icd10")
    private ICD10_mapping icd10;

    private Boolean visited;

    private Date followupdate;

    private int doctorid;

    private String priority ;

    @OneToOne(mappedBy = "followupid")
    private diagnose_id_gen diagnoseIdGen;

}