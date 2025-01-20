package com.yc.Rhythm.service.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yc.Rhythm.dto.req.CategoryRequest;
import com.yc.Rhythm.dto.res.CategoryResponse;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(String id, CategoryRequest request);
    void deleteCategory(String id);
    CategoryResponse getCategoryById(String id);
    Page<CategoryResponse> getAllCategories(Pageable pageable);
}

