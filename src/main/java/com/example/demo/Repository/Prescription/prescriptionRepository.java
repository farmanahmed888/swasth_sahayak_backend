package com.example.demo.Repository.Prescription;

import com.example.demo.Entity.patient.prescriptionTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface prescriptionRepository extends JpaRepository<prescriptionTable,Integer> {
    List<prescriptionTable> findAllByPid(int pid);

    List<prescriptionTable> findAllByDiseasename(String disease);
    List<prescriptionTable> findAllByPid(Integer disease);
    List<prescriptionTable> findFirst3ByPidOrderByPrescriptiondateDesc(int pid);
    prescriptionTable findFirst1ByPidOrderByPrescriptiondateDesc(int pid);

    List<prescriptionTable> findAllByDoctorid(String d);
    List<prescriptionTable> findFirstByPidOrderByPrescriptiondateDesc(int pid);
    List<prescriptionTable> findAllByDoctoridOrderByPrescriptionidDesc(String d);

    List<prescriptionTable> findTop3ByDoctoridOrderByPrescriptionidDesc(String d);



}
