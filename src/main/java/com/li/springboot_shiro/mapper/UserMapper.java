package com.li.springboot_shiro.mapper;

import com.li.springboot_shiro.domain.User;

/**
 * @Description:
 * @Author: li
 * @Create: 2020-02-16 18:56
 */
public interface UserMapper {
    User findByName(String name);

    Integer addUser(User user);
}
