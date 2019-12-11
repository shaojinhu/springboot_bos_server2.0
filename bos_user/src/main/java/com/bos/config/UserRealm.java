package com.bos.config;

import com.bos.pojo.user.User;
import com.bos.shiro.MyRealm;
import com.bos.user.service.UserService;
import com.bos.util.MD5Util;
import org.apache.shiro.authc.*;

import javax.annotation.Resource;

/**
 * 用于用户登录认证的Realm
 *  继承MyRealm
 */
public class UserRealm extends MyRealm {

    @Resource
    private UserService userService;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得用户名密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username =usernamePasswordToken.getUsername();
        String password =String.valueOf(usernamePasswordToken.getPassword());
        //根据用户名查询用户
        User user = userService.getUserByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            //装载用户的权限
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            return info;
        }
        //否则返回null 会报异常，controller层进行捕获并返回消息
        return null;
    }
}
