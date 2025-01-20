package com.yc.Rhythm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yc.Rhythm.entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}

