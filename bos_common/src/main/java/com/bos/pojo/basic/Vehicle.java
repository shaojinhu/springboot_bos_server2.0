package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 车辆管理Vehicle实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vehicle")
public class Vehicle implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "route_type")
    private String routeType;  //线路类型

    @TableField(value = "route_name")
    private String routeName; //线路名称

    @TableField(value = "shipper")
    private String shipper;  //承运商

    @TableField(value = "driver")
    private String driver;  //司机

    @TableField(value = "vehicle_num")
    private String vehicleNum;  //车牌号

    @TableField(value = "vehicle_type")
    private String vehicleType;  //车型

    @TableField(value = "ton")
    private String ton;  //吨控

    @TableField(value = "remark")
    private String remark;   //备注

    @TableField(value = "operating_company")
    private String operatingCompany;

    @TableField(value = "operating_time")
    private String operatingTime;

    @TableField(value = "operator")
    private String operator;
}
