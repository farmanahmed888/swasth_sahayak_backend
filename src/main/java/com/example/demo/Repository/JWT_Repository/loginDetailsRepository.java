package com.example.demo.Repository.JWT_Repository;

import com.example.demo.DTO.JWT_DTO.LoginDTO;
import com.example.demo.Entity.JWT_entity.loginDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface loginDetailsRepository extends JpaRepository<loginDetails,String> {
    public loginDetails findByUsername(String usr);
}
