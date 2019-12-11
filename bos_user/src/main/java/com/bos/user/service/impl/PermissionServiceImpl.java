package com.bos.user.service.impl;

import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.user.mapper.PermissMapper;
import com.bos.user.repository.PermissionRepository;
import com.bos.user.repository.RoleRepository;
import com.bos.user.service.PermissionService;
import com.bos.util.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理的Service
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissMapper permissMapper;
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private IdWorker idWorker;

    /**
     * 通过递归获取Permission集合
     * @return
     */
    @Override
    public Result getPermisionList(){
        List<Permission> permissionsList = permissMapper.selectList(null);
        //调用递归方法，生成父子权限
        //首先获取非1的权限
        final List<Permission> permiss = permissionsList.stream().filter(item -> !item.getPtype().equals("1")).collect(Collectors.toList());
        //获得根菜单
        List<Permission> permissOne = permissionsList.stream().filter(item -> item.getPtype().equals("1")).collect(Collectors.toList());
        permissOne.parallelStream().forEach(item ->{
            setChild(item,permiss);
        });
        return new Result(ResultCode.SUCCESS,permissOne);
    }

    /**
     * 添加Permission权限
     * @param permission
     * @return
     */
    @Override
    public Result addPermission(Permission permission) {
        //指定id
        permission.setPid(idWorker.nextId()+"");
        int insert = permissMapper.insert(permission);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }


    /**
     * 修改Permission权限
     */
    @Override
    public Result updatePermission(Permission permission) {
        Permission target = permissionRepository.findById(permission.getPid()).get();
        BeanUtils.copyProperties(permission,target);
        try {
            permissionRepository.save(target);
            return Result.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAIL();
        }
    }

    /**
     * 删除Permission
     * @param permission
     * @return
     */
    @Override
    public Result deletePermission(Permission permission) {
        //首先查看是否有子级，有则不让删除
        List<Permission> permissions = permissionRepository.getPermissionByParentid(String.valueOf(permission.getPid()));
        if(permissions.size() >0 &&  permissions!= null) return new Result(ResultCode.HASCHILD_IS_NOTDELETE);//包含子级不可删除
        //查看是否有角色关联
        Integer count = permissMapper.getRolesByPid(permission.getPid());
        if(count > 0) return new Result(ResultCode.PERMISSION_IS_HAVE_ROLE);
        //进行删除
        int i = permissMapper.deleteById(permission.getPid());
        if(i > 0) return new Result(ResultCode.SUCCESS);
        else return new Result(ResultCode.FAIL);
    }


    /**
     * 递归方法
     * @param permissions
     */
    private void setChild(Permission p,List<Permission> permissions){
        //获取与父id相等的list
        List<Permission> list = permissions.parallelStream().filter(item -> item.getParentid().equals(p.getPid())).collect(Collectors.toList());
        p.setChildPerList(list);
        if(list!=null){
            list.parallelStream().forEach(item ->{
                setChild(item,permissions);
            });
        }
    }



}
