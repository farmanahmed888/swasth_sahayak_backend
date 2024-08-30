package com.example.demo.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.demo.Entity.JWT_entity.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

@Component
public class JwtHelper {

    //requirement :

    private final ObjectMapper objectMapper;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    @Value("${jwt.secret}")
    private String secret;

    public JwtHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public RoleType getRoleFromToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            String roleString = claims.get("role", String.class);
            return RoleType.valueOf(roleString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    public String getRoleStringFromToken(String token) {
//        return (String) getClaimFromToken(token, claims -> claims.get("role", RoleType.class));
//    }



    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails, RoleType role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());
        return doGenerateToken(claims, userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) {
            return false;
        }

        try {
            String username = getUsernameFromToken(token);

            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            // Log the exception or handle it as appropriate for your application
            return false;
        }
    }

  public String extractToken(HttpServletRequest request)
  {
      String authorizationHeader = request.getHeader("Authorization");


      if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
          return authorizationHeader.substring(7);
      }


      return null;
  }

}