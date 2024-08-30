package com.example.demo.Twilio;

import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {
    @Value("${twilio.fromNumber}")
    private String fromNumber;

//@PostMapping("/smsCheck")
    public ResponseEntity<String>  setBody(@RequestBody String mobileNo, String name, String fieldworkerMob, String fieldworkerName, String token) {
        TwilioRequest twilioRequest = new TwilioRequest();
        twilioRequest.setToPhoneNumber(mobileNo);
        fromNumber="+13343263039";
        twilioRequest.setFromPhoneNumber(fromNumber);

        String msg ="\n-"+"\nHi " + name + "," +
                "\nYour follow-up appointment is scheduled today. Please use the following OTP to confirm the visit: " + token + ".\n" +
                "Fieldworker Details:\n" +
                "Name: " + fieldworkerName +
                "\nMobile: " + fieldworkerMob +
                "\nIf you have any questions, feel free to contact us." +
                "\nBest regards, \nTeam Swasth Sahayak\n +918708094071\nswasth.sahayak@gmail.com";
    twilioRequest.setMessage(msg);

    System.out.println(msg);

    ResponseEntity<String> responseEntity = sendMessage(twilioRequest);
    System.out.println(responseEntity);
    return responseEntity;

    }


    public ResponseEntity<String> sendMessage(TwilioRequest twilioRequest) {

        try {
            // Check if RequestBody has valid data or NOT
//            String fromNumber="+13343263039";

            // Extract Request Data
            String toNumber = twilioRequest.getToPhoneNumber();
            String fromNumber=twilioRequest.getFromPhoneNumber();
            String msg = twilioRequest.getMessage();

            // Create Message to be sent
            Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber), msg).create();

            return ResponseEntity.ok("SMS sent successfully!");
        } catch (TwilioException e) {
            // Handle TwilioException, log the error, and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions, log the error, and return appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
