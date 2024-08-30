package com.example.demo.Repository.Doctor;

import com.example.demo.Entity.doctor.doctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


public interface DoctorRepository extends JpaRepository<doctorDetails,Integer> {
    List<doctorDetails> findAllByBlockcode(int blockcode);
    doctorDetails findByDid(int id);

}
