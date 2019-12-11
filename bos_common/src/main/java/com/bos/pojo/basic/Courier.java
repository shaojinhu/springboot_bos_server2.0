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
 * 快递员Courier
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "courier")
public class Courier implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "courier_num")
    private String courierNum;

    @TableField(value = "name")
    private String name;

    @TableField(value = "check_pwd")
    private String checkPwd;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "company_name")
    private String companyName;

    @TableField(value = "deltag")
    private String deltag;

    @TableField(value = "type")
    private String type;

    @TableField(value = "vehicle_type")
    private String vehicleType;

    @TableField(value = "vehicle_num")
    private String vehicleNum;

    @TableField(value = "standard_id")
    private String standardId;

    @TableField(value = "taketime_id")
    private String takeTimeId;

//    @TableField(value = "vehicle_id")
//    private String vehicleId;

    @TableField(value = "pdanum")
    private String pdaNum;

}
