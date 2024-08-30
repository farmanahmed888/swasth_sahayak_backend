package com.example.demo.Controller.JWTController;

import com.example.demo.DTO.JWT_DTO.JWTRequestRefreshDTO;
import com.example.demo.DTO.JWT_DTO.JWTResponseRefreshDTO;
import com.example.demo.DTO.JWT_DTO.LoginDTO;
import com.example.demo.Entity.JWT_entity.RefreshToken;
import com.example.demo.Entity.JWT_entity.RoleType;
import com.example.demo.Entity.JWT_entity.loginDetails;
import com.example.demo.Entity.fieldworker.fieldWorkerIdEncrypt;
import com.example.demo.JWT.JwtRequestDTO;
import com.example.demo.JWT.JwtResponseDTO;
import com.example.demo.JWT.JwtHelper;
import com.example.demo.JWTService.LogoutService;
import com.example.demo.JWTService.RefreshService;
import com.example.demo.JWTService.UserdetailsService;
import com.example.demo.Repository.JWT_Repository.loginDetailsRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private com.example.demo.Repository.field_worker_Repositary.fieldWorkerEncrpt fieldWorkerEncrpt;
    @Autowired
    private loginDetailsRepository repo;
    @Autowired
    private UserdetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    RefreshService refreshService;
    @Autowired
    LogoutService logoutService;
//    @Autowired
//    private loginDetails loginDetails;


//    @Autowired
//    loginDetails loginDetails;


    @Autowired
    private com.example.demo.Repository.JWT_Repository.loginDetailsRepository loginDetailsRepository;


    public AuthController(UserdetailsService userDetailsService, AuthenticationManager manager, JwtHelper helper){
        this.userDetailsService = userDetailsService;
        this.helper = helper;
        this.manager = manager;
    }
    @PostMapping("/insert")
    public loginDetails admindetail(@RequestBody loginDetails login) {
//        loginDetails ln = new loginDetails();
//        ln.setUsername(l.getUsername());
//        ln.setPassword(l.getPassword());
//        ln.setRole(l.getRole());
        loginDetailsRepository.save(login);
        return login;
    }
    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody JwtRequestDTO request) throws BadCredentialsException {
        try {
            // Perform authentication
            this.doAuthenticate(request.getUsername(), request.getPassword());
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            // Generate JWT token

            RefreshToken refreshToken = refreshService.createRefreshToken(userDetails.getUsername());
            // Build response DTO
            String role = userDetails.getAuthorities().isEmpty() ? "" : userDetails.getAuthorities().iterator().next().getAuthority();
            RoleType rl = stringToRole(role);
            System.out.println(rl);
            String token = helper.generateToken(userDetails,rl);
            JwtResponseDTO response = new JwtResponseDTO();

            response.setRefreshToken(refreshToken);
            response.setJwtToken(token);
            response.setRole(rl);
            System.out.println(request.getUsername());
            fieldWorkerIdEncrypt fw=fieldWorkerEncrpt.findByFieldworkerid(request.getUsername());
            System.out.println(fw);
            if(fw!=null)response.setName(fw.getFid().getName());
            else response.setName(null);
            response.setUsername(userDetails.getUsername());
            System.out.println(loginDetailsRepository.findByUsername(request.getUsername()).getRole().name());
            if(loginDetailsRepository.findByUsername(request.getUsername()).getRole().name().equals(request.getRole()))
            return response;
            else return new JwtResponseDTO();
        } catch (BadCredentialsException e) {

          throw e;
        } catch (Exception e) {
           throw e;
        }
    }

@PostMapping("/refreshToken")
        public JWTResponseRefreshDTO refreshToken(@RequestBody JWTRequestRefreshDTO requestRefreshDTO ){
         return refreshService.findByToken(requestRefreshDTO.getToken())
                 .map(refreshService::verifyExpiration)
                 .map(RefreshToken::getLogins)
                 .map(loginDetails -> {
                     UserDetails userDetails = userDetailsService.loadUserByUsername(loginDetails.getUsername());
                     String role = userDetails.getAuthorities().isEmpty() ? "" : userDetails.getAuthorities().iterator().next().getAuthority();
                     RoleType rl = stringToRole(role);
                     String accessToken = helper.generateToken(userDetails,rl);
                     return JWTResponseRefreshDTO.builder()
                             .accesstoken(accessToken)
                             .refreshtoken(requestRefreshDTO.getToken()).build();
                 }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
}


@PostMapping("/logout")
public ResponseEntity<String> logout(HttpServletRequest request)
{
    String Token = helper.extractToken(request);
    if(Token == null)
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There was no token present in the header");

    else
    {
        logoutService.addToBlacklist(Token);
        return ResponseEntity.ok("Logged out Successfully");
    }

}




    private void doAuthenticate(String username, String password) {
      loginDetails temp =  repo.findByUsername(username);
        if (temp != null) {
            // Comparing passwords using equals method
            if (password.equals(temp.getPassword())) {
                // Authentication logic if passwords match
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
                manager.authenticate(authentication);
            } else {
                // Handle incorrect password
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            // Handle user not found
            throw new UsernameNotFoundException("User not found");
        }
    }
    public RoleType stringToRole(String roleString) {
        switch (roleString) {
            case "ADMIN":
                return RoleType.ADMIN;
            case "FIELDWORKER":
                return RoleType.FIELDWORKER;
            case "DOCTOR":
                return RoleType.DOCTOR;
            case "SUPERVISOR":
                return RoleType.SUPERVISOR;
        }
        return RoleType.NORMAL_USER;
    }

}