package com.yc.Rhythm.service.Interfaces;

import com.yc.Rhythm.entity.Role;
import java.util.List;
import java.util.Set;

import com.yc.Rhythm.dto.res.UserResponseDto;

public interface IUserService {
    List<UserResponseDto> findAllUsers();
    UserResponseDto updateUserRoles(String username, Set<Role> newRoles);
}
