package com.bos.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bos.pojo.user.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissMapper extends BaseMapper<Permission> {


    //查询用户权限。根据用户id
    @Select("<script>" +
            "SELECT p.* FROM permission p,role r,role_permission rp,user_role ur,USER u " +
            "WHERE p.`pid` = rp.`permissionid` AND " +
            "rp.`roleid` = r.`rid` AND " +
            "r.`rid` = ur.`roleid` AND " +
            "ur.`userid` = u.`userid` AND " +
            "u.`userid` = #{userid}" +
            "</script>")
    public List<Permission> getPermission(@Param("userid") String userid);

    @Select("SELECT COUNT(1) FROM `permission` AS p,`role_permission` AS rp " +
            "WHERE p.`pid` = rp.`permissionid` AND p.`pid` = #{ id }")
    Integer getRolesByPid(@Param("id") String pid);
}
