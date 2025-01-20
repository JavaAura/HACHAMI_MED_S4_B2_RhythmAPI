package com.yc.Rhythm.dto.req;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class UpdateUserRolesRequest {
    @NotEmpty(message = "Roles cannot be empty")
    private Set<String> roleNames;

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }
}

