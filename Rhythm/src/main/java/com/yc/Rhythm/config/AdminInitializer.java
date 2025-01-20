package com.yc.Rhythm.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.User;
import com.yc.Rhythm.entity.enums.ERole;
import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Check if admin role exists, if not create it
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.toString())
                .orElseGet(() -> {
                    Role newAdminRole = new Role(ERole.ROLE_ADMIN);
                    return roleRepository.save(newAdminRole);
                });

        // Check if user role exists, if not create it
        Role userRole = roleRepository.findByName(ERole.ROLE_USER.toString())
                .orElseGet(() -> {
                    Role newUserRole = new Role(ERole.ROLE_USER);
                    return roleRepository.save(newUserRole);
                });

        // Check if admin user exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminPassword")); // You should change this password

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Admin user has been initialized.");
        }
    }
}

