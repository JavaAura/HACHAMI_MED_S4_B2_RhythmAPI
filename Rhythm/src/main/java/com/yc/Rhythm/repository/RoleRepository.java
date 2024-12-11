package com.yc.Rhythm.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yc.Rhythm.entity.Role;
import com.yc.Rhythm.entity.enums.ERole;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
  }