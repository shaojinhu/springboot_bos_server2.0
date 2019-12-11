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
import java.util.HashSet;
import java.util.Set;

/**
 * 角色Role
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "role")
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @TableId(value = "rid",type = IdType.ID_WORKER_STR)
    private String rid;

    @Column(name = "rname")
    @TableField(value = "rname")
    private String rname;

    @Column(name = "rdesc")
    @TableField(value = "rdesc")
    private String rdesc;

    /**
     * 多对多映射role_permission表
     */
    @TableField(exist = false)
    @ManyToMany
    @JoinTable(name = "role_permission",joinColumns = @JoinColumn(name = "roleid",referencedColumnName = "rid"),
                inverseJoinColumns = @JoinColumn(name = "permissionid",referencedColumnName = "pid"))
    private Set<Permission> permissions = new HashSet<>();

    //进用于添加角色时映射权限
    @TableField(exist = false)
    @Transient
    private String permissionIds;
}
