package com.example.demo.Repository.Followup;

import com.example.demo.Entity.patient.Artifacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface artifactsRepository extends JpaRepository<Artifacts,Integer> {
    List<Artifacts> findAllByPidAndDate(int id, Date d);
}
