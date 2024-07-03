package com.pbo.restaurant.repository;

import com.pbo.restaurant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
