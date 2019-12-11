package com.bos.user.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import com.bos.pojo.user.User;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.user.mapper.PermissMapper;
import com.bos.user.mapper.UserMapper;
import com.bos.user.repository.PermissionRepository;
import com.bos.user.repository.RoleRepository;
import com.bos.user.service.RoleService;
import com.bos.util.IdWorker;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@SuppressWarnings("all")
public class RoleServiceImpl implements RoleService {


    @Resource
    private RoleRepository roleRepository;
    @Resource
    private IdWorker IdWorker;//id生成工具类，生成全局唯一id
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private UserMapper userMapper;
    /**
     * 获取Role的集合，有分页
     * @param map
     * @return
     */
    @Override
    public Result getRoleList(Map<String,String> map){
        //分页
        Pageable pageable = PageRequest.of(Integer.parseInt(map.get("page"))-1,Integer.parseInt(map.get("size")),new Sort(Sort.Direction.DESC,"rid"));
        //查询全部
        Page<Role> all = roleRepository.findAll(pageable);
        PageResult<Role> rolePageResult = new PageResult<>(all.getTotalElements(),all.getContent());
        return new Result(ResultCode.SUCCESS,rolePageResult);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Override
    public Result addRole(Role role) {
        //获取ID
        String l = IdWorker.nextId()+"";
        role.setRid(l);
        //解析权限
        Set<Permission> pers = new HashSet<>();
        String[] split = role.getPermissionIds().split(",");
        for (String s : split) {
            Permission permission = permissionRepository.findById(s).get();
            pers.add(permission);
        }
        if(pers.size() > 0 && ObjectUtils.anyNotNull(pers)){
            role.setPermissions(pers);
            //添加操作
            roleRepository.save(role);
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.FAIL);
    }

    /**
     * 修改角色
     * @return
     */
    @Override
    public Result updateRole(Role role) {
        //首先查询
        Role target = roleRepository.findById(role.getRid()).get();
        BeanUtils.copyProperties(role,target);
        //解析权限
        Set<Permission> permissions = new HashSet<>();
        String[] split = role.getPermissionIds().split(",");
        for (String s : split) {
            Permission permission = permissionRepository.findById(s).get();
            permissions.add(permission);
        }
        if(permissions.size() >0 && ObjectUtils.anyNotNull(permissions)){
            target.setPermissions(permissions);
            roleRepository.save(target);
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.FAIL);
    }

    /**
     * 删除Role
     * @param role
     * @return
     */
    @Override
    public Result deleteRole(Role role) {
        //首先查看Role是否被用户使用,有则不让删除
        List<User> users = userMapper.getUserByList(role.getRid());
        if(users.size()>0 && ObjectUtils.anyNotNull(users)){
            return Result.FAIL();
        }
        //执行删除会同时删除role表和permission_role表
        roleRepository.deleteById(role.getRid());
        return Result.SUCCESS();

    }

    /**
     * 获得全部的角色
     * @return
     */
    @Override
    public Result getRole() {
        List<Role> all = roleRepository.findAll();
        return new Result(ResultCode.SUCCESS,all);
    }


}
