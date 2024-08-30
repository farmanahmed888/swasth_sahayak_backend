package com.example.demo.Email;

// Importing required classes
import com.example.demo.Email.EmailDetails;
// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}