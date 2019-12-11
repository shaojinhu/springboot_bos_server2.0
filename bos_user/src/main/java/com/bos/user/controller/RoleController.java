package com.bos.user.controller;

import com.bos.pojo.user.Role;
import com.bos.response.Result;
import com.bos.user.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 获取Role的集合，有分页
     * @param map  分页条件
     * @return
     */
    @PostMapping("getRoleList")
    public Result getRoleList(@RequestBody Map<String,String> map){
        return roleService.getRoleList(map);
    }

    @GetMapping("getRole")
    public Result getRole(){
        return roleService.getRole();
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public Result addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping("updateRole")
    public Result updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    /**
     * 删除Role
     * @param role
     * @return
     */
    @DeleteMapping("deleteRole")
    public Result deleteRole(@RequestBody Role role){
        return roleService.deleteRole(role);
    }
}
