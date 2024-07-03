package com.pbo.restaurant.controller;

import jakarta.validation.Valid;
import com.pbo.restaurant.dto.UserDto;
import com.pbo.restaurant.entity.Product;
import com.pbo.restaurant.entity.User;
import com.pbo.restaurant.service.UserService;
import com.pbo.restaurant.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pbo.restaurant.service.CategoryService;
import com.pbo.restaurant.service.ProductService;

import java.util.List;

@SuppressWarnings("unused")
@Controller
public class AuthController {
    private UserService userService;
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public void setUserService(UserService userService, ProductService productService,
            CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // handler method to handle home page reques
    @GetMapping("/")
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index"; // Sesuaikan dengan nama template Thymeleaf yang Anda gunakan
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin() {
        return "redirect:/products";
    }
}