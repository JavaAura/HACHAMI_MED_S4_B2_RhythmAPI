package com.yc.Rhythm.controller;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.Rhythm.dto.req.LoginRequest;
import com.yc.Rhythm.dto.req.SignupRequest;
import com.yc.Rhythm.dto.res.JwtResponse;
import com.yc.Rhythm.dto.res.MessageResponse;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.User;
import com.yc.Rhythm.entity.enums.ERole;
import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.repository.UserRepository;
import com.yc.Rhythm.security.jwt.JwtUtils;
import com.yc.Rhythm.security.service.UserDetailsImpl;
import com.yc.Rhythm.service.AuthService;
import com.yc.Rhythm.service.Interfaces.IAuthSerivce;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private AuthService authService;


	public AuthController(AuthService authService){
		this.authService = authService;
	}

	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        try {
            JwtResponse jwtResponse = authService.register(signupRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the user.");
        }
    }

    /**
     * Handles user login.
     *
     * @param loginRequest The login request payload
     * @return A response entity with the JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.login(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while logging in the user.");
        }
    }
}
