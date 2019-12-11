package com.bos.user.repository;

import com.bos.pojo.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role,String>, JpaSpecificationExecutor<Role> {


}
