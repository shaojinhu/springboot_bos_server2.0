package com.bos.util;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Component
public class JWTUtils {

  //签名私钥
  private static String key = "bosserver";
  //签名的失效时间 1 天
  private static Long ttl = 60 * 60 * 1000L * 24;

  /**
   * 设置认证token
   *      id:登录用户id
   *      nikename：登录用户名
   *      map：用户的权限
   */
  public static String createJwt(String id, String name, Map<String,Object> map) {
    //1.设置失效时间
    long now = System.currentTimeMillis();//当前毫秒
    long exp = now + ttl;
    //2.创建jwtBuilder
    JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                            .setIssuedAt(new Date())
                            .signWith(SignatureAlgorithm.HS256,key);  //指定算法
    //3.根据map设置claims
    if(map != null){
      for(Map.Entry<String,Object> entry : map.entrySet()) {
        jwtBuilder.claim(entry.getKey(),entry.getValue());
      }
    }
    //设置过期时间
    jwtBuilder.setExpiration(new Date(exp));
    //4.创建token
    String token = jwtBuilder.compact();

    return token;
  }


  /**
   * 解析token字符串获取clamis  在这里处理token过期
   */
  public static Claims parseJwt(String token) {
    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    return claims;
  }

  /**
   * 从HttpRequest请求中获得Claims
   * @param request
   * @return
   */
  public static Claims getClaims(HttpServletRequest request){
    String authorization = request.getHeader("Authorization");
    String token = authorization.replace("Bearer ","");
    Claims currentClaims = JWTUtils.parseJwt(token);
    return currentClaims;
  }
}
