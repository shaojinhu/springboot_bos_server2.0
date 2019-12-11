package com.bos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

  private boolean success;
  private Integer code;
  private String message;
  private Object data;


  public Result(ResultCode code){
    this.success = code.success;
    this.code = code.code;
    this.message = code.message;
  }

  public Result(ResultCode code,Object data){
    this.success = code.success;
    this.code = code.code;
    this.message = code.message;
    this.data = data;
  }




  public static Result SUCCESS(){
    return new Result(ResultCode.SUCCESS);
  }

  public static Result FAIL(){
    return new Result(ResultCode.FAIL);
  }

}
