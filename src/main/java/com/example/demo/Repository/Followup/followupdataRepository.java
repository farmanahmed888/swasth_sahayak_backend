package com.example.demo.Repository.Followup;

import com.example.demo.Entity.FollowUp.followUpData;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import com.example.demo.Entity.patient.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface followupdataRepository extends JpaRepository<followUpData,Integer> {
    long countByDoctoridAndAndFollowupdate(int did, Date d);

    followUpData findByFollowupid(int followUpId);

    @Query(value = "SELECT  followupid FROM follow_up_data WHERE followupdate = :date", nativeQuery = true)
    List<Integer> findAllDistinctPatientIds(@Param("date") Date date);

    followUpData findByPatientidAndAndFollowupdate(PatientDetails pid, Date d);

    List<followUpData> findByFieldworkeridAndAndFollowupdate(fieldworkerDetails fid, Date d);

    List<followUpData> findAllByPatientidAndFieldworkeridAndDiagnoseIdGenIsNull(PatientDetails p,fieldworkerDetails f);

    List<followUpData> findAllByDoctoridAndFollowupdate(int p,Date f);


    List<followUpData> findAllByPatientidAndVisitedOrderByFollowupdateAsc(PatientDetails pid, boolean status);
    List<followUpData>findAllByFollowupdateAndVisited(Date date, boolean status);

}


