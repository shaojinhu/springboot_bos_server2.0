package com.bos.user.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登出接口
 */
@RestController
public class LogoutController {

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public void logout(){
        SecurityUtils.getSubject().logout();
    }

}
