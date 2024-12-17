package com.yc.Rhythm.dto.res;

public class RoleResponse {
    
    private String id;
    private String name;

    public RoleResponse() {}

    public RoleResponse(String id, String name) {
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
