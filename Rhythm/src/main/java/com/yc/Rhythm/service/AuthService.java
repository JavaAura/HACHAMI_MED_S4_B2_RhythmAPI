package com.yc.Rhythm.service;

import com.yc.Rhythm.dto.req.LoginRequest;
import com.yc.Rhythm.dto.req.SignupRequest;
import com.yc.Rhythm.dto.res.JwtResponse;
import com.yc.Rhythm.entity.User;
import com.yc.Rhythm.entity.Role;

import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.repository.UserRepository;
import com.yc.Rhythm.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder,
        JwtUtils jwtService,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

        // Assign roles
        Set<Role> roles = signupRequest.getRoles().stream()
            .map(roleName -> roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " not found!")))
            .collect(Collectors.toSet());

        user.setRoles(roles);

        // Save user to the repository
        userRepository.save(user);

        // Generate token
        var jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList())
        ));

        return new JwtResponse(
            jwtToken,
            user.getUsername(),
            user.getEmail(),
            roles.stream().map(role -> role.getName().name()).collect(Collectors.toList())
        );
    }

 
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
                )
            );

            var user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Error: User not found"));

            var jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList())
            ));

            return new JwtResponse(
                jwtToken,
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
