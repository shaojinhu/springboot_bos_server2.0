package com.bos.base;

import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * shiro授权失败执行的controller
 * 在shiro的授权方法授权失败后，会根据shiro配置类中配置的授权失败跳转的地址进行跳转到此controller触发接口
 */
@RestController
public class ErrorController {


    @GetMapping("autherror")
    public Result autherror(){
        return new Result(ResultCode.AUTHERROR);
    }

}
