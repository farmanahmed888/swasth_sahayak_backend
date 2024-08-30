package com.example.demo.Controller.JWTController;

import com.example.demo.Email.EmailDetails;
import com.example.demo.Entity.JWT_entity.loginDetails;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
@CrossOrigin("*")
public class forgotpasswordController {

    private final com.example.demo.Repository.JWT_Repository.loginDetailsRepository loginDetailsRepository;

    public forgotpasswordController(loginDetailsRepository loginDetailsRepository) {
        this.loginDetailsRepository = loginDetailsRepository;
    }
    @Autowired
    private com.example.demo.Email.EmailServiceImpl EmailServiceImpl;
    @PostMapping("/verifymail/{id}")
    public ResponseEntity<String> verifymail(@PathVariable String id){
        loginDetails l=loginDetailsRepository.findByUsername(id);
        if(l==null)return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
        String pass = alphaNumericString(6);
        EmailDetails details=new EmailDetails();
        details.setRecipient(id);
        details.setSubject("Reset Your Password");
        details.setMsgBody(emailOTPBody("recipient",id,pass));
        EmailServiceImpl.sendSimpleMail(details);

        return new ResponseEntity<>(pass,HttpStatus.OK);

    }

    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
    public String emailOTPBody(String recipientName, String username, String otp) {
        String emailMessage = "Dear " + recipientName + ",\n\n" +
                "We hope this email finds you well.\n\n" +
                "As requested, here is the One-Time Password (OTP) to reset your password for your Swasth Sahayak account:\n\n" +
                "Username: " + username + "\n" +
                "OTP: " + otp + "\n\n" +
                "Please use this OTP to proceed with resetting your password. This OTP is valid for 10 minutes and should not be shared with anyone else. " +
                "If you did not request a password reset, please ignore this email or contact us immediately at swasth.sahayak@gmail.com.\n\n" +
                "Thank you for choosing Swasth Sahayak. We are committed to ensuring the security of your account and providing you with the best healthcare assistance and support.\n\n" +
                "Warm regards,\n" +
                "The Swasth Sahayak Team";

        System.out.println(emailMessage);
        return emailMessage;
    }

}
