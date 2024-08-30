package com.example.demo.EncDec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncServiceLayer {
    @Autowired com.example.demo.EncDec.EncryptTest EncryptTest;
    public String enc(String input){
       return EncryptTest.convertToDatabaseColumn(input);
    }
}
