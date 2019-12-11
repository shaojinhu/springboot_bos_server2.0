package com.bos.user.service;

import com.bos.execption.MyException;
import com.bos.pojo.user.User;
import com.bos.response.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

//    Result login(User user) throws Exception;

    Result getPermission(User user);

    Result addUser(User user);

    Result updateUser(User user);

    Result deleteUser(User user);

    Result getUserList(Map<String, String> map);

    User getUserByUsername(String username);
}
