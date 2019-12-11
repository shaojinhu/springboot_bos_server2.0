package com.bos.execption;

import com.bos.response.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义异常类
 */
@Data
public class MyException extends Exception implements Serializable {

        private ResultCode resultCode;

        public MyException(ResultCode resultCode){
            this.resultCode = resultCode;
        }
}
