package com.bos.user.repository;

import com.bos.pojo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    //根据用户名称查询用户
    User findByUsername(String username);
}
