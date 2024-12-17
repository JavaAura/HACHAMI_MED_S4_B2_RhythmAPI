package com.yc.Rhythm.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yc.Rhythm.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String roleName);
}