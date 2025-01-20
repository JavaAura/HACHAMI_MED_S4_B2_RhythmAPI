package com.yc.Rhythm.controller.admin;

import com.yc.Rhythm.dto.req.UpdateUserRolesRequest;
import com.yc.Rhythm.dto.res.UserResponseDto;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.service.Interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "User Management", description = "Operations for managing users and their roles")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final IUserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(IUserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/roles")
    @Operation(summary = "Update user roles", description = "Update the roles of a specific user")
    public ResponseEntity<UserResponseDto> updateUserRoles(
            @PathVariable("id") String userId,
            @Valid @RequestBody UpdateUserRolesRequest request) {
        try {
            Set<Role> newRoles = request.getRoleNames().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            UserResponseDto updatedUser = userService.updateUserRoles(userId, newRoles);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

