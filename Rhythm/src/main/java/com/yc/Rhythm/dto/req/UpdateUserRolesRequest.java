package com.yc.Rhythm.dto.req;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class UpdateUserRolesRequest {
    @NotEmpty(message = "Roles cannot be empty")
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

