package com.pbo.restaurant.service;

import com.pbo.restaurant.dto.UserDto;
import com.pbo.restaurant.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}