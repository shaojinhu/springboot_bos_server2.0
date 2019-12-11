package com.bos.shiro;

import com.bos.pojo.user.Role;
import com.bos.pojo.user.User;
import com.bos.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Shiro的自定义Realm，登陆的认证和访问接口的授权方法
 */
public class MyRealm extends AuthorizingRealm {

    public void setName(String name) {
        super.setName("MyRealm");
    }

    /**
     * 授权方法，根据接口上的shiro注解来触发此方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();
        ProfileResult profileResult = new ProfileResult(user);
        //获取角色集合
        //Set<String> roles = (Set<String>)profileResult.getMap().get("roles");
        //获取权限集合，这里只获取api权限
        Set<String> apis = (Set<String>)profileResult.getMap().get("apis");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.setRoles();//设置角色结合
        info.setStringPermissions(apis);//设置权限
        return info;
    }

    /**
     * 登陆的认证方法,在其子类中进行实现
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
