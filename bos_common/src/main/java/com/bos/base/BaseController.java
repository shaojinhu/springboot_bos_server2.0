package com.bos.base;

import com.bos.pojo.user.User;
import com.bos.util.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseController {

    protected String userId;
    protected String company;
    protected String operatingTime;
    protected Claims claims;
    protected String nikeName;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected Map<String,String> map;


    @ModelAttribute
    public void setRequest(HttpServletRequest request){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        this.userId = user.getUserid();//获得当前登录的用户ID
        this.company = "暂无部门";  //获得当前登录用户的部门
        this.operatingTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); //获得当前时间
        this.nikeName = user.getNikename(); //获得用户名
        Map<String,String> map = new HashMap<>();
        map.put("userId",this.userId);
        map.put("company",this.company);
        map.put("operatingTime",this.operatingTime);
        map.put("nikename",this.nikeName);
        this.map = map;
    }


}
