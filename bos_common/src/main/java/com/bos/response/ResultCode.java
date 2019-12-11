package com.bos.response;


public enum ResultCode {


  SUCCESS(true,10000,"恭喜你,操作成功"),
  FAIL(false,10001,"操作失败,请检查你的网络"),
  LOGIN_USER_NOT_ENABLE_STATE(false,80088,"登录用户不存在或密码错误或被禁用"),
  LOGIN_IS_EXCEPTION(false,80088,"登录异常，请从新登陆"),
  PERMISSION_IS_NULL(false,80088,"用户权限不足"),
  HASCHILD_IS_NOTDELETE(false,80089,"包含子级，不可以删除"),
  PERMISSION_IS_HAVE_ROLE(false,90089,"该权限有角色正在使用"),
  DEPA_HAVE_USER(false,90089,"该部门下存在员工，不可删除"),
  STANDARD_USERING(false,90087,"该标准正在使用，不允许删除"),
  TAKETIME_USERING(false,90086,"该时间正在使用，不允许此操作"),
  VEHICLE_USERING(false,90085,"该车辆正在使用，不允许删除"),
  CITY_IS_EXIT(false,90185,"区域已经存在，不允许重复添加"),
  DEPA_IS_NOTHAVE_USER(false,92089,"该部门下没有员工"),
  DISABLED_IS_NOT_UPDATE(false,91089,"禁用状态不可修改"),
  TOKEN_IS_NULL(false,20022,"TOKEN为空"),
  AUTHERROR(false,20021,"授权失败");

  int code;
  String message;
  boolean success;



  ResultCode(boolean success, int code, String message){
    this.success = success;
    this.code = code;
    this.message = message;
  }

  public int code(){
    return code;
  }

  public String message(){
    return message;
  }

  public boolean success(){
    return success;
  }
}
