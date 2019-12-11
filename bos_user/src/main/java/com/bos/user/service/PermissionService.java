package com.bos.user.service;

import com.bos.pojo.user.Permission;
import com.bos.response.Result;

public interface PermissionService {

    public Result getPermisionList();


    Result addPermission(Permission permission);

    Result updatePermission(Permission permission);

    Result deletePermission(Permission permission);
}
