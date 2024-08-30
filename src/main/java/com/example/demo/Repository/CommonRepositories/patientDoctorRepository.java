package com.example.demo.Repository.CommonRepositories;

import com.example.demo.Entity.common_entities.patientDoctorMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface patientDoctorRepository extends JpaRepository<patientDoctorMapping,Integer> {
   List<patientDoctorMapping> findTop3ByDoctorid(int doctor_id);
   List<patientDoctorMapping> findAllByDoctorid(int doctor_id);
   long countByDoctorid(int doctor_id);

   patientDoctorMapping findByPid(Integer i);
}
