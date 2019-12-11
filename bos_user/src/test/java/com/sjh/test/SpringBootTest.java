package com.sjh.test;

import com.bos.SpringBootBosApplication;
import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import com.bos.user.mapper.PermissMapper;
import com.bos.user.repository.DepaRepository;
import com.bos.user.repository.PermissionRepository;
import com.bos.user.repository.RoleRepository;
import com.bos.user.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.boot.test.context.SpringBootTest(classes = SpringBootBosApplication.class)
@RunWith(SpringRunner.class)
public class SpringBootTest {

    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private PermissMapper permissMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleRepository roleRepository;

    @Test
    public void testJpa(){
        List<Permission> all = permissionRepository.findAll();
        System.out.println(all);
    }

//    @Test
//    public void testRole(){
//        //List<Permission> permissions = permissionRepository.getPermissionByParentid("1");
//        permissMapper.getRolesByPid(1);
//        System.out.println(permissMapper.getRolesByPid(1));
//    }
    @Resource
    private DepaRepository depaRepository;

    @Test
    public void testDepa(){
        List<String> userListByDepaId = depaRepository.getUserListByDepaId("7890");
        System.out.println(userListByDepaId);
    }
}
