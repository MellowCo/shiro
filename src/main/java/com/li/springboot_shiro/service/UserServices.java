package com.li.springboot_shiro.service;

import com.li.springboot_shiro.domain.User;
import com.li.springboot_shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: li
 * @Create: 2020-02-16 19:00
 */
@Service
public class UserServices {
    @Autowired
    private UserMapper mapper;

    public User findByName(String name) {
        return mapper.findByName(name);
    }

    public Integer addUser(User user) {
        return mapper.addUser(user);
    }
}
