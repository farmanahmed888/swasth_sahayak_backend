package com.example.demo.Twilio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TwilioRequest {
    private  String toPhoneNumber;
    private  String fromPhoneNumber;
    private  String message;
}