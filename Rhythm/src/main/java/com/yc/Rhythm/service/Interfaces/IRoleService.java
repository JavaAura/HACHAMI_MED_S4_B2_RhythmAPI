package com.yc.Rhythm.service.Interfaces;

import java.util.List;

import com.yc.Rhythm.dto.req.RoleRequest;
import com.yc.Rhythm.dto.res.RoleResponse;
import com.yc.Rhythm.entity.Role;

public interface IRoleService {
    public List<RoleResponse> getAllRoles();
    public RoleResponse createRole(RoleRequest request);
    public Role findById(String id);
    public Role findByName(String name);
}