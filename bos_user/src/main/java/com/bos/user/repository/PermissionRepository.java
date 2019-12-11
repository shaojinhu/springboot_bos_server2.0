package com.bos.user.repository;

import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PermissionRepository extends JpaRepository<Permission,String>,JpaSpecificationExecutor<Permission> {


    List<Permission> getPermissionByParentid(String parentid);


    List<Role> getRolesByPid(Integer pid);
}
