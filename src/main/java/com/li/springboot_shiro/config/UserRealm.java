package com.li.springboot_shiro.config;

import com.li.springboot_shiro.domain.User;
import com.li.springboot_shiro.service.UserServices;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: li
 * @Create: 2020-02-16 17:09
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserServices services;


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");
        /**
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        //principal 是认证时 SimpleAuthenticationInfo 添加的一个选项
        User principal = (User)subject.getPrincipal();
        //添加权限
        info.addStringPermission(principal.getPerms());
        **/

        //添加角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole("vip1");
        info.addRole("vip2");
        info.addRole("vip3");

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");

        //获取用户名
        UsernamePasswordToken passwordToken= (UsernamePasswordToken)token;
        String userName = passwordToken.getUsername();
        //查询数据库
        User user = services.findByName(userName);

        if (user == null){
            //用户名不存在
            //shiro底层会抛出UnKnowAccountException
            return null;
        }

        //2、判断密码, 这里的user是principal
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
        System.out.println(info);
        System.out.println(userName);
        return info;
    }

    public static void main(String[] args) {
        SimpleHash hash = new SimpleHash("MD5","qwer",null,1024);
        System.out.println(hash);
    }
}
