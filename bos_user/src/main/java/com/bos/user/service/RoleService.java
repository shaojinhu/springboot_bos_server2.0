package com.bos.user.service;

import com.bos.pojo.user.Role;
import com.bos.response.Result;

import java.util.Map;

public interface RoleService {

    public Result getRoleList(Map<String,String> map);

    Result addRole(Role role);

    Result updateRole(Role role);

    Result deleteRole(Role role);

    Result getRole();
}
