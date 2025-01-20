package com.yc.Rhythm.service;

import com.yc.Rhythm.dto.req.LoginRequest;
import com.yc.Rhythm.dto.req.SignupRequest;
import com.yc.Rhythm.dto.res.JwtResponse;
import com.yc.Rhythm.entity.User;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.enums.ERole;
import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.repository.UserRepository;
import com.yc.Rhythm.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse register(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create a new user
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Assign default USER role
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER.toString())
                .orElseThrow(() -> new RuntimeException("Error: User Role not found."));
        roles.add(userRole);
        user.setRoles(roles);

        // Save user to the repository
        userRepository.save(user);

        // Generate token
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());

        return new JwtResponse(
                jwtToken,
                user.getUsername(),
                user.getEmail(),
                roles.stream().map(role -> role.getName().name()).collect(Collectors.toList())
        );
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found"));

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwtToken,
                user.getUsername(),
                user.getEmail(),
                roles
        );
    }
}

