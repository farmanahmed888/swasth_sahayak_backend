package com.example.demo.JWTService;

import com.example.demo.Entity.JWT_entity.loginDetails;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class UserdetailsService implements UserDetailsService {
    @Autowired
    private loginDetailsRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        loginDetails userOptional = repo.findByUsername(username);

        if (userOptional == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


        return new User(
                userOptional.getUsername(),
                userOptional.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userOptional.getRole().toString()))
        );
    }



}
