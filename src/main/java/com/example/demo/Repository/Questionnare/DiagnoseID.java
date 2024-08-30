package com.example.demo.Repository.Questionnare;

import com.example.demo.Entity.Questionnaire.diagnose_id_gen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnoseID extends JpaRepository<diagnose_id_gen,Integer> {
    public List<diagnose_id_gen> findAllByPid(Integer i);
}
