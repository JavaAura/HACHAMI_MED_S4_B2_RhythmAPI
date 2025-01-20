package com.yc.Rhythm.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yc.Rhythm.dto.res.UserResponseDto;
import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.User;
import com.yc.Rhythm.repository.UserRepository;
import com.yc.Rhythm.service.Interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
        .map(user -> new UserResponseDto(
            user.getUsername(),
            user.getEmail(),
            user.getRoles()
        ))
        .collect(Collectors.toList());

    }

    @Override
    public UserResponseDto updateUserRoles(String userId, Set<Role> newRoles) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with userId: " + userId);
        }

        User user = userOptional.get();

        user.setRoles(newRoles);

        return new UserResponseDto(user.getUsername(), user.getEmail(), user.getRoles());
    }
    
}