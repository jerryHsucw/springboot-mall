package com.JerryHsu.springboot_mall.service;

import com.JerryHsu.springboot_mall.dao.dto.UserLoginRequest;
import com.JerryHsu.springboot_mall.dao.dto.UserRegisterRequest;
import com.JerryHsu.springboot_mall.model.User;


public interface UserService {

    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User login(UserLoginRequest userLoginRequest);
}
