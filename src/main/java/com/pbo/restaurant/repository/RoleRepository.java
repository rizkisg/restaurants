package com.pbo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pbo.restaurant.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}