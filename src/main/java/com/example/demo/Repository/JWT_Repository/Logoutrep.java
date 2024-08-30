package com.example.demo.Repository.JWT_Repository;

public interface Logoutrep {
    void addToBlacklist(String Token);
    boolean isBlacklisted(String Token);
}
