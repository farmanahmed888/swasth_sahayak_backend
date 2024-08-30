package com.example.demo.Repository.JWT_Repository;

import com.example.demo.Entity.JWT_entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenrep extends CrudRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    List<RefreshToken> findAllByLogins(loginDetails l);
}
