package com.pbo.restaurant.repository;

import com.pbo.restaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
