package com.yc.Rhythm.Mapper;

import org.mapstruct.Mapper;

import com.yc.Rhythm.dto.req.RoleRequest;
import com.yc.Rhythm.dto.res.RoleResponse;
import com.yc.Rhythm.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    public RoleResponse toResponse(Role role);

    public Role toEntity(RoleRequest request);
}
