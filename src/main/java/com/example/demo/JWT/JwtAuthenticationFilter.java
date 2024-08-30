package com.example.demo.JWT;
import com.example.demo.JWTService.LogoutService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Entity.JWT_entity.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.management.relation.Role;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private LogoutService logoutservice;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            String token = requestHeader.substring(7);
            String username = null;
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Illegal Argument: {}", e.getMessage());
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                try {
                    userDetails = this.userDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e) {
                    logger.error("User not found: {}", e.getMessage());
                }

                if (userDetails != null && this.jwtHelper.validateToken(token, userDetails) && !logoutservice.isBlacklisted(token) ) {
                    RoleType requiredRole = getRequiredRole(request);
                    RoleType userRole = this.jwtHelper.getRoleFromToken(token);
                    System.out.println(userRole);
                    System.out.println(requiredRole);

                    if (userRole != null && userRole.equals(requiredRole)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                    else
                        logger.warn("You are not authorised to access this resource" + userRole + requiredRole);
                } else {
                    logger.warn("Token Validation fails or user details are null!");
                }
            }
        } else {
            logger.warn("Invalid Header Value");
        }

        filterChain.doFilter(request, response);
    }

    private RoleType getRequiredRole(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/admin")) {
            return RoleType.ADMIN;
        }
        else if (requestURI.contains("/doctor")) {
            return RoleType.DOCTOR;
        }
        else if(requestURI.contains("/supervisor")) {
            return RoleType.SUPERVISOR;
        }
        else if(requestURI.contains("/fieldworker")) {
            return RoleType.FIELDWORKER;
        }

        return RoleType.NORMAL_USER;
    }

    }
