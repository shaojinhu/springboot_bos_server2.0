package com.bos.user.controller;

import com.bos.execption.MyException;
import com.bos.pojo.user.User;
import com.bos.response.ProfileResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.user.service.UserService;
import com.bos.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录接口
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            //生成token
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, MD5Util.encrypt(password));
            //进行方法
            SecurityUtils.getSubject().login(usernamePasswordToken);
            //获得sessionID
            //SecurityUtils.getSubject().getSession().setTimeout(10000L);设置sesion的过期时间
            String sessionId =(String)SecurityUtils.getSubject().getSession().getId();
            return new Result(ResultCode.SUCCESS,sessionId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new Result(ResultCode.LOGIN_USER_NOT_ENABLE_STATE);//返回登录错误消息
        }
    }


    /**
     * 获取用户User的权限Permission
     * @return
     * @throws MyException
     */

    @RequestMapping(value = "/getPermission",method = RequestMethod.POST)
    public  Result getPermission(){
        //获得主体
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            ProfileResult profileResult = new ProfileResult(user);
            return new Result(ResultCode.SUCCESS,profileResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.PERMISSION_IS_NULL);
        }
    }

    /**
     * 获得用户列表
     * @param map
     * @return
     */
    @PostMapping("getUserList")
    public Result getUserList(@RequestBody Map<String,String> map){
        return userService.getUserList(map);
    }


    /**
     * 添加用户User
     * @param user
     * @return
     */
    @PostMapping("addUser")
    public Result adduser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 修改用户User
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 删除用户User
     * @param user
     * @return
     */
    @DeleteMapping("deleteUser")
    public Result deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    /**
     * 邮箱验证码
     */
    @GetMapping("sendMailNumber/{mail}/{username}")
    public Result sendMailNumber(@PathVariable("mail")String mail,@PathVariable("username")String username){
        return userService.sendMailNumber(mail,username);
    }

    /**
     * 邮箱验证码修改密码
     * @param map
     * @return
     * @throws MyException
     */
    @PostMapping("updatePasswordByNumber")
    public Result updatePasswordByNumber(@RequestBody Map<String,String> map) throws MyException {
        return userService.updatePasswordByMail(map);
    }

    @PostMapping("updatePassword")
    public Result updatePassword(@RequestBody Map<String,String> map){
        return userService.updatePassword(map);
    }
}
