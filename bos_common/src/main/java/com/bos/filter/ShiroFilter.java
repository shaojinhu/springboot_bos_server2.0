package com.bos.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * shiro过滤器，需要授权的请求都会经过此过滤器
 * 在ShiroConfig配置类中进行配置
 */
public class ShiroFilter extends FormAuthenticationFilter {


    public ShiroFilter() {
        super();
    }
    /**
     * 解决OPTIONS请求的问题
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){//查看请求是否是OPTIONS请求
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();

        writer.write("  no no no");
        writer.close();
        return false;
    }
}
