package com.example.demo.Repository.Patient;

import com.example.demo.Entity.patient.Artifacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface arifactsrepositary extends JpaRepository<Artifacts,Integer> {
    List<Artifacts> findAllByPidAndDate(int id, Date d);

}
