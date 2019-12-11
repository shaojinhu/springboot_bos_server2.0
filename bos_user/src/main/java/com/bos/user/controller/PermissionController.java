package com.bos.user.controller;

import com.bos.pojo.user.Permission;
import com.bos.response.Result;
import com.bos.user.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 获取Permission列表
     */
    @PostMapping("getPermissionList")
    public Result getPermissionList(){
        return permissionService.getPermisionList();
    }

    /**
     * 添加Permission
     * @param permission
     * @return
     */
    @PostMapping("addPermission")
    public Result addPermission(@RequestBody Permission permission){
        return permissionService.addPermission(permission);
    }

    /**
     * 修改Permission
     * @param permission
     * @return
     */
    @PutMapping("updatePermission")
    public Result updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission);
    }

    /**
     * 删除Permission
     */
    @DeleteMapping("deletePermission")
    public Result deletePermission(@RequestBody Permission permission){
        return permissionService.deletePermission(permission);
    }
}
