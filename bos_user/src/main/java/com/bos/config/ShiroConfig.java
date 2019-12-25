package com.bos.config;

import com.bos.filter.ShiroFilter;
import com.bos.shiro.CustomSessionManager;
import com.bos.shiro.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 定义自定Realm
     * @return
     */
    @Bean
    public MyRealm getMyRealm(){
        return new UserRealm();
    }

    /**
     * 设置Shiro的SecurityManager安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(getMyRealm());//将自定义realm添加入shiro安全管理器中
        //TODO 设置会话管理器
        defaultWebSecurityManager.setSessionManager(sessionManager());//添加配置好的会话管理器
        //TODO 设置缓存管理器
        defaultWebSecurityManager.setCacheManager(redisCacheManager());//添加配置好的缓存管理器
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager());//设置注入：SecurityManager
        //设置授权失败后跳转的地址
        filterFactoryBean.setUnauthorizedUrl("/autherror");

        //设置过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc",new ShiroFilter());
        filterFactoryBean.setFilters(filters);

        //设置过滤链条
        Map<String,String> filterMap = new LinkedHashMap<>();
        //放行接口  anon为放行  authc为拦截
        filterMap.put("/user/login","anon");
        filterMap.put("/user/sendMailNumber/**","anon");
        filterMap.put("/user/updatePasswordByNumber","anon");
//        filterMap.put("/logout","logout");

        //这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterMap.put("/**", "authc");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return filterFactoryBean;
    }

//----------------------配置缓存管理器--------------------------------
    /**
     * 配置shiro中缓存对redis的支持
     * @return
     */
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setDatabase(0);
        redisManager.setTimeout(50000);
        redisManager.setPassword(null);
        return redisManager;
    }

    /**
     * 配置缓存管理器
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());//设置缓存
        redisCacheManager.setPrincipalIdFieldName("userid");//用户的userid作为缓存对象的key
        //redisCacheManager.setKeyPrefix("");缓存用户信息的前缀
        return redisCacheManager;
    }

//----------------------------配置会话管理器-------------------------------

    /**
     * 设置sessionDao
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(-1);
        return redisSessionDAO;
    }

    /**
     * 配置sessionManager会话管理器
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        //将我们外部创建的sessionManager会话管管理器进行使用
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(redisSessionDAO());//设置sessionDao
//        customSessionManager.setGlobalSessionTimeout(-10000L);//设置session的过期时间 默认1800000L
        customSessionManager.setDeleteInvalidSessions(true);//是否开启删除无效的session对象  默认为true
        return customSessionManager;
    }


    /**
     * 开启Shiro的注解支持
     *(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
