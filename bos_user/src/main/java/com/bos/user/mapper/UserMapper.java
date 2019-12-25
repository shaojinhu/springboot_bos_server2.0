package com.bos.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bos.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    @Select("SELECT * FROM `user_role` AS ur,`user` AS u " +
            "WHERE ur.`userid` = u.`userid` " +
            "AND ur.`roleid` = #{rid}")
    List<User> getUserByList(@Param("rid") String rid);

    @Update("UPDATE " +
            "  `bos`.`user` " +
            "SET " +
            "  `password` = #{password} " +
            "WHERE `mail` = #{mail} AND `username` = #{username} ")
    Integer updatePasswrodByNumber(@Param("password")String password,@Param("username") String username,@Param("mail")String mail);
}
