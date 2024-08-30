package com.example.demo.Repository.Doctor;

import com.example.demo.Entity.common_entities.patientDoctorMapping;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface patientDoctorMappingRepository extends JpaRepository<patientDoctorMapping,Integer> {
    patientDoctorMapping findByPid(Integer abhaid);
}
