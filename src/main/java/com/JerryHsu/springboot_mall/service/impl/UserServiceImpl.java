package com.JerryHsu.springboot_mall.service.impl;

import com.JerryHsu.springboot_mall.dao.UserDao;
import com.JerryHsu.springboot_mall.dao.dto.UserLoginRequest;
import com.JerryHsu.springboot_mall.dao.dto.UserRegisterRequest;
import com.JerryHsu.springboot_mall.model.User;
import com.JerryHsu.springboot_mall.service.UserService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        //檢查email是否被註冊
        User user = userDao.getUserEmail(userRegisterRequest.getEmail());

        if (user != null){
            log.warn("該email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //註冊會員
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        //檢查email是否被註冊
        User user = userDao.getUserEmail(userLoginRequest.getEmail());

        if (user == null) {
            log.warn("該email {} 尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (userLoginRequest.getPassword().equals(user.getPassword())){
            return user;
        }
        else {
            log.warn("該email {} 密碼不正確",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}


