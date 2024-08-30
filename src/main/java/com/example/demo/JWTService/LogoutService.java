package com.example.demo.JWTService;

import com.example.demo.Repository.JWT_Repository.Logoutrep;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LogoutService implements Logoutrep {
private Set<String> blacklist = new HashSet<>();
    @Override
    public void addToBlacklist(String Token) {
                blacklist.add(Token);
    }

    @Override
    public boolean isBlacklisted(String Token) {
        return blacklist.contains(Token);
    }
}
