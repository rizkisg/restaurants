package com.pbo.restaurant.service;

import com.pbo.restaurant.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product saveProduct(Product product);

    Product findProductById(Long id);

    void deleteProductById(Long id);
}
