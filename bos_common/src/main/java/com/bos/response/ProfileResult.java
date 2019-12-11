package com.bos.response;

import com.bos.pojo.user.Permission;
import com.bos.pojo.user.Role;
import com.bos.pojo.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

/**
 * 权限构造类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuppressWarnings("all")//重复代码压制警告
public class ProfileResult implements Serializable {

    private String nickName;
    private String userName;
    private Map<String,Object> map = new HashMap<>();//用于封装权限集合，菜单集合、按钮集合、api集合

    public ProfileResult(User user, List<Permission> list) {
        this.nickName = user.getNikename();
        this.userName = user.getUsername();

        Set<String> menus = new HashSet<>();//菜单Code
        Set<String> button = new HashSet<>();//按钮Code
        Set<String> apis = new HashSet<>(); //访问后台的api Code

        if (list != null && list.size() > 0) {
            //给权限分类
            for (Permission perm : list) {
                String code = perm.getPpermiss();
                String type = perm.getPtype();  // 1和2分别为一级菜单、二级菜单  3为按钮权限  4为api权限
                if (type.equals("1") || type.equals("2")){
                    menus.add(code);
                }else if(type.equals("3")) {
                    button.add(code);
                }else if(type.equals("4")) {
                    apis.add(code);
                }
            }
        }

        this.map.put("menus",menus);
        this.map.put("buttons",button);
        this.map.put("apis",apis);
    }

    public ProfileResult(User user) {
        this.nickName = user.getNikename();
        this.userName = user.getUsername();

        Set<Role> userRoles = new HashSet<>();

        Set<String> roles = new HashSet<>();//角色Code
        Set<String> menus = new HashSet<>();//菜单Code
        Set<String> button = new HashSet<>();//按钮Code
        Set<String> apis = new HashSet<>(); //访问后台的api Code

        if (user.getRoles()!= null && user.getRoles().size() > 0) {
            userRoles = user.getRoles();
            //给权限分类
            for (Role role : userRoles) {
//                roles.add(role.getRname());
                Set<Permission> permissions = role.getPermissions();
                for (Permission perm : permissions) {
                    String code = perm.getPpermiss();
                    String type = perm.getPtype();  // 1和2分别为一级菜单、二级菜单  3为按钮权限  4为api权限
                    if (type.equals("1") || type.equals("2")){
                        menus.add(code);
                    }else if(type.equals("3")) {
                        button.add(code);
                    }else if(type.equals("4")) {
                        apis.add(code);
                    }
                }
            }
        }

        this.map.put("menus",menus);
        this.map.put("buttons",button);
        this.map.put("apis",apis);
        this.map.put("roles",roles);
    }
}
