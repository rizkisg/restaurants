package com.pbo.restaurant.controller;

import com.pbo.restaurant.entity.Category;
import com.pbo.restaurant.entity.Product;
import com.pbo.restaurant.service.CategoryService;
import com.pbo.restaurant.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // Menampilkan semua produk
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "products"; // Sesuaikan dengan nama template Thymeleaf yang Anda gunakan
    }

    // Menampilkan form untuk membuat produk baru
    @GetMapping("/buat")
    public String showCreateProductForm(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "create_product"; // Sesuaikan dengan nama template Thymeleaf yang Anda gunakan
    }

    // Menyimpan produk baru beserta gambar
    // Menyimpan produk baru beserta gambar
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
            @RequestParam("image") MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
                Path uploadPath = Paths.get(uploadDir);

                // Memastikan direktori upload ada, jika tidak, buat direktori
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Copy file ke direktori upload
                try (InputStream inputStream = imageFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    product.setImageUrl("/uploads/" + fileName); // Simpan path relatif ke dalam entitas produk
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Menampilkan form untuk mengedit produk
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_product"; // Sesuaikan dengan nama template Thymeleaf yang Anda gunakan
    }

    // Menyimpan perubahan pada produk yang telah diedit
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
                Path uploadPath = Paths.get(uploadDir);

                // Memastikan direktori upload ada, jika tidak, buat direktori
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Copy file ke direktori upload
                try (InputStream inputStream = imageFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    product.setImageUrl("/uploads/" + fileName); // Simpan path relatif ke dalam entitas produk
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Menghapus produk
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
}
