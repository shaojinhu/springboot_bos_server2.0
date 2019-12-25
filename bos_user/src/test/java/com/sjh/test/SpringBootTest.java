package com.sjh.test;

import com.alibaba.fastjson.JSONObject;
import com.bos.SpringBootBosApplication;
import com.bos.basic.mapper.SubAreaMapper;
import com.bos.pojo.basic.SubArea;
import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import com.bos.pojo.user.User;
import com.bos.user.mapper.PermissMapper;
import com.bos.user.repository.DepaRepository;
import com.bos.user.repository.PermissionRepository;
import com.bos.user.repository.RoleRepository;
import com.bos.user.service.RoleService;
import com.bos.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = SpringBootBosApplication.class)
public class SpringBootTest {

    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private PermissMapper permissMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MailUtil mailUtil;
    @Resource
    private SubAreaMapper subAreaMapper;

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

    @Test
    public  void testRedis(){
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123123");
        redisTemplate.opsForValue().set("user",user);
//        redisTemplate.opsForValue().set("user","1231231");
    }

    @Test
    public void  testRedisGet(){
        String str = redisTemplate.opsForValue().get("user").toString();
        System.out.println(str);
    }

    @Test
    public void testSendMail(){
        mailUtil.sendMail("1585273221@qq.com","这是一封邮件的主题","这是一个封邮件的内容");
    }

    @Test
    public void testCol(){
        String col = "assist_key_word ,key_word";
        subAreaMapper.getSubAreaListByCol(col);
    }
}
