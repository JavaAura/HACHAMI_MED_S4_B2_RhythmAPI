package com.yc.Rhythm.service.Interfaces;

import java.util.List;
import java.util.Set;

import com.yc.Rhythm.dto.res.UserResponseDto;
import com.yc.Rhythm.entity.Role;

public interface IUserService {
    List<UserResponseDto> findAllUsers();
    UserResponseDto updateUserRoles(String userId, Set<Role> newRoles);
}