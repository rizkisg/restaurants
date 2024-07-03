package com.pbo.restaurant.service;

import com.pbo.restaurant.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category saveCategory(Category category);

    List<Category> findAll();
}
