package com.li.springboot_shiro.controller;

import com.li.springboot_shiro.domain.User;
import com.li.springboot_shiro.service.UserServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @Description:
 * @Author: li
 * @Create: 2020-02-16 17:25
 */
@Controller
public class UserController {

    @Autowired
    UserServices services;

    @GetMapping("/toLogin")
    public String toLogin() {
        return "/login";
    }

    @GetMapping("/index")
    public String index() {
        return "/login";
    }


    @RequestMapping("/add")
    public String add() {
        return "/user/userAdd";
    }

    @RequestMapping("/del")
    public String del() {
        return "/user/userDel";
    }


    @RequestMapping("login")
    public String login(String name, String pwd, boolean rememberMe, Model model) {
        //登录认证
        //1 创建1个Subject
        Subject subject = SecurityUtils.getSubject();

        //2 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd, rememberMe);

        //3 执行登录
        try {
            subject.login(token);
            //登录成功
            return "/index";
        } catch (UnknownAccountException e) {
            //UnknownAccountException异常
            //表示登录失败，用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "/login";
        } catch (IncorrectCredentialsException e) {
            //表示登录失败，密码错误
            model.addAttribute("msg", "密码错误");
            return "/login";
        }
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "/noAuth";
    }

    @PostMapping("/addUser")
    public String addUser(User user, Model model) {
        //随机生成salt
        user.setSalt(UUID.randomUUID().toString());
        //使用Shiro工具转化salt
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        //生成机密后的密码
        SimpleHash md5 = new SimpleHash("MD5", user.getPassword(), salt, 1024);

        user.setPassword(md5.toString());
        Integer integer = services.addUser(user);

        System.out.println("controller addUser-" + integer);
        model.addAttribute("msg", "添加成功");
        return "/login";
    }

    @RequestMapping("/rember")
    public String rember() {
        return "/rember";
    }

}
