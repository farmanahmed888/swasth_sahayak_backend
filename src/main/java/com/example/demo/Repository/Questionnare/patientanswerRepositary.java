package com.example.demo.Repository.Questionnare;

import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entity.Questionnaire.patient_answers;

import java.util.List;

public interface patientanswerRepositary extends JpaRepository<patient_answers,Integer> {

//    List<patient_answers> findAllByDiagnoseid(diagnose_id_gen d);
}
