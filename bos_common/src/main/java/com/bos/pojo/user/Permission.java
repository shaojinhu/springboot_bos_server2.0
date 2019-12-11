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
import java.util.List;
import java.util.Set;

/**
 * 权限Permission
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "permission")
@Entity
@Table(name = "permission")
@ToString
public class Permission implements Serializable {

    @Id
    @TableId(value = "pid",type = IdType.ID_WORKER)
    private String pid;

    @Column(name = "pname")
    @TableField(value = "pname")
    private String pname;

    @Column(name = "ppermiss")
    @TableField(value = "ppermiss")
    private String ppermiss;

    @Column(name = "ptype")
    @TableField(value = "ptype")
    private String ptype;

    @Column(name = "parentid")
    @TableField(value = "parentid")
    private String parentid;

    @Column(name = "pdesc")
    @TableField(value = "pdesc")
    private String pdesc;

    @Column(name = "is_show")
    @TableField(value = "is_show")
    private String isShow;

    @Transient
    @TableField(exist = false)
    private List<Permission> childPerList;

//    @TableField(exist = false)
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "role_permission",joinColumns = @JoinColumn(name = "permissionid",referencedColumnName = "pid"),
//            inverseJoinColumns = @JoinColumn(name="roleid",referencedColumnName = "rid"))
//    private Set<Role> roles = new HashSet<>();

}
