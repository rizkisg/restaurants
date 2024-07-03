package com.pbo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pbo.restaurant.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}