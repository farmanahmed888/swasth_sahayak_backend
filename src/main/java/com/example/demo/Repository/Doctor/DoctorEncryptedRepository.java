package com.example.demo.Repository.Doctor;

import com.example.demo.Entity.doctor.DoctorEncryptId;
import com.example.demo.Entity.doctor.doctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorEncryptedRepository extends JpaRepository<DoctorEncryptId,Integer> {
    public DoctorEncryptId findByUsername(String s);
    public DoctorEncryptId findByDid(doctorDetails s);

}
