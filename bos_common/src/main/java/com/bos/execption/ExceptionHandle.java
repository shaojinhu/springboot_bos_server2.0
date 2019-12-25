package com.bos.execption;

import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获类
 */
//@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler({MyException.class,Exception.class})//设置捕获异常类型
    @ResponseBody
    public Result Handle(HttpServletRequest request, HttpServletResponse response, Exception e){
        Result r = null;
        System.err.println(e.getClass());
        if(e.getClass() == MyException.class){
            r = new Result(((MyException)e).getResultCode());
        }else{
            r = new Result(ResultCode.FAIL);
        }
        return r;
    }

    @ExceptionHandler({AuthenticationException.class})//设置捕获异常类型
    @ResponseBody
    public Result HandleTwo(HttpServletRequest request, HttpServletResponse response, Exception e){
        return new Result(ResultCode.LOGIN_USER_NOT_ENABLE_STATE);
    }


    @ExceptionHandler({AuthorizationException.class})//设置捕获异常类型
    @ResponseBody
    public Result HandleThree(HttpServletRequest request, HttpServletResponse response, Exception e){
        return new Result(ResultCode.AUTHERROR);
    }
}
