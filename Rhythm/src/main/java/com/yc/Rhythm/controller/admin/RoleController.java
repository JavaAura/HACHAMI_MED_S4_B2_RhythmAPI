package com.yc.Rhythm.controller.admin;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yc.Rhythm.dto.req.RoleRequest;
import com.yc.Rhythm.dto.res.RoleResponse;
import com.yc.Rhythm.dto.res.UserResponseDto;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.service.Interfaces.IUserService;
import com.yc.Rhythm.service.RoleServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Role Controller", description = "Gestion des rôles par l'administrateur")
@RequestMapping("/api/admin/roles")
public class RoleController {
     
    
    private final RoleServiceImpl roleService;

    private final IUserService userService;

    public RoleController(RoleServiceImpl roleService ,IUserService userService){
        this.roleService = roleService;
        this.userService = userService;
    }


    /***
     * Get all roles    
     * @return
     */
    @Operation(summary = "Get all roles", description = "Récupérer tous les rôles")
    @ApiResponse(responseCode = "200", description = "Rôles récupérés avec succès")
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /***
     * Create a new role
     * @param request The role creation request
     * @return The created role response
     */
    @Operation(summary = "Create a new role", description = "Créer un nouveau rôle")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide - Erreurs de validation")
    @ApiResponse(responseCode = "409", description = "Rôle déjà existant")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @Operation(summary = "Update user roles", description = "Update roles for a specific user")
    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDto> updateRoles(
            @Parameter(description = "Username of the user to update") @PathVariable String username,
            @Parameter(description = "List of roles to assign to the user") @RequestBody Set<Role> roles) {
        try {
            UserResponseDto updatedUser = userService.updateUserRoles(username, roles);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
