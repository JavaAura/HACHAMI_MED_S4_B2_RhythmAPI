package com.yc.Rhythm.dto.res;

public class RoleResponse {
    private String id;
    private String name;

    // Default constructor
    public RoleResponse() {}

    // Constructor with parameters
    public RoleResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "RoleResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

