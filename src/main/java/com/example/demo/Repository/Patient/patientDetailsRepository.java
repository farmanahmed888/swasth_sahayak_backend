package com.example.demo.Repository.Patient;

import com.example.demo.Entity.patient.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface patientDetailsRepository extends JpaRepository<PatientDetails, Integer> {
    PatientDetails findByAbhaid(String abha_id);
    List<PatientDetails> findAllByBlockcode(int blockcode);
    PatientDetails findByPid(int it);
}
