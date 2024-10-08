package com.example.demo;

import com.example.demo.Entity.JWT_entity.loginDetails;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import com.twilio.Twilio;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.EncDec.Encrypt;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@RestController
public class SwasthSahayakBackendApplication {
	//private final static Dotenv dotenv = Dotenv.configure().load();
	private final static String ACCOUNT_SID = "AC5aad81a899d633bee1bab677dd72b807";
	private final static String AUTH_ID = "1b37860a474c1aaa2b9df04fe3484e8b";
	static {
		Twilio.init(ACCOUNT_SID, AUTH_ID);
	}

	private final com.example.demo.Repository.JWT_Repository.loginDetailsRepository loginDetailsRepository;

	public SwasthSahayakBackendApplication(loginDetailsRepository loginDetailsRepository) {
		this.loginDetailsRepository = loginDetailsRepository;
	}

	public static void main(String[] args) {

		SpringApplication.run(SwasthSahayakBackendApplication.class, args);
	}
	@GetMapping("/")
	public String index() {
		return "Welcome to Swasth Sahayak Home";
	}



}
