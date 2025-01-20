package com.yc.Rhythm.dto.res;

import java.util.Set;
import java.util.stream.Collectors;

import com.yc.Rhythm.entity.Role;

public class UserResponseDto {

    private String username;
    private String email;
    private Set<String> roles;

    public UserResponseDto() {}

    public UserResponseDto(String username, String email, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles.stream()
                          .map(role -> role.getName().name())
                          .collect(Collectors.toSet());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream()
                          .map(role -> role.getName().name())
                          .collect(Collectors.toSet());
    }
}