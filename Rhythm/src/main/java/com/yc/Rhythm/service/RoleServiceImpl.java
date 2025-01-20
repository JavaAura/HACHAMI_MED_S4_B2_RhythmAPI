package com.yc.Rhythm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yc.Rhythm.Mapper.RoleMapper;
import com.yc.Rhythm.dto.req.RoleRequest;
import com.yc.Rhythm.dto.res.RoleResponse;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.repository.RoleRepository;
import com.yc.Rhythm.service.Interfaces.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {


    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository , RoleMapper roleMapper){
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
    
        request.setName("ROLE_" + request.getName());
  
        if(roleRepository.findByName(request.getName()).isPresent()){
            throw new RuntimeException("Role already exists");
        }
        Role role = roleMapper.toEntity(request);
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
    }

}