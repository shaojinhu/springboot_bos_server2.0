package com.bos.user.repository;

import com.bos.pojo.user.Depa;
import com.bos.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DepaRepository extends JpaRepository<Depa,String>, JpaSpecificationExecutor<Depa> {

    //查看制定部门下的用户数量
    @Query(nativeQuery = true ,value = "SELECT COUNT(du.userid) FROM depa_user AS du,depa AS d WHERE du.depaid = d.did AND d.did = :did")
    public Integer getUserByDepaId(@Param("did") String did);

    @Query(nativeQuery = true ,value = "SELECT u.userid FROM depa AS d,USER AS u WHERE d.did = u.depaid AND d.did = ?")
    public List<String> getUserListByDepaId(String did);
}
