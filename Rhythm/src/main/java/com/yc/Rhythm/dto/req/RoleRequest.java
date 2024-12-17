package com.yc.Rhythm.dto.req;

import javax.validation.constraints.NotBlank;

public class RoleRequest {
      private String id;

    @NotBlank(message = "Le nom du rôle est obligatoire")
    private String name;

    // No-argument constructor
    public RoleRequest() {}

    // All-arguments constructor
    public RoleRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
