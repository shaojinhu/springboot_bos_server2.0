package com.bos.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户User
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "user")
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @TableId(value = "userid",type= IdType.ID_WORKER)
    private String userid;

    @Column(name = "username")
    @TableField(value = "username")
    private String username;

    @Column(name = "password")
    @TableField(value = "password")
    private String password;

    @Column(name = "nikename")
    @TableField(value = "nikename")
    private String nikename;

    @Column(name = "depaid")
    @TableField(value = "depaid")
    private String depaId;

    /**
     * 用于映射user_role表
     */
    @TableField(exist = false)
    @ManyToMany
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "userid",referencedColumnName = "userid"),
                inverseJoinColumns = @JoinColumn(name = "roleid",referencedColumnName = "rid"))
    private Set<Role> roles = new HashSet<>();

    //用于添加修改用户映射roleid
    @TableField(exist = false)
    @Transient
    private String roleIds;


}
