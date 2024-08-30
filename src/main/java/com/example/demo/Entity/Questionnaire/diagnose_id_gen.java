package com.example.demo.Entity.Questionnaire;

import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.common_entities.patientDoctorMapping;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class diagnose_id_gen {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int diagnosis_id;

 @OneToMany(mappedBy = "diagnoseid")
 private List<patient_answers> diagid;

 private String ICD10;

 private int pid; //save patient_id only
 private int fieldworkerid;

 private Date date;

 private String fieldworkerComment;


 @OneToOne
 @JoinColumn(name = "followupid")
 private followUpData followupid;


 //foreign key to follow_up data
// private int follow_up_Id;

 //diagnose_Id ka will be copied to prescription table just when doctor prescribes
}
