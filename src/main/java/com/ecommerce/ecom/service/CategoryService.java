package com.ecommerce.ecom.service;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDto;
import com.ecommerce.ecom.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    public CategoryResponse showALlCategories();
    public CategoryDto addCategory(CategoryDto categoryDto);
    public String deleteCategory(Long id);

    public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto);
}
