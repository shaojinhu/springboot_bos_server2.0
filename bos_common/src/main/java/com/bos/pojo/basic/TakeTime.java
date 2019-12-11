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
 * 收派时间管理TakeTime
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "take_time")
public class TakeTime implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "normal_duty_time")
    private String normalDutyTime;

    @TableField(value = "normal_work_time")
    private String normalWorkTime;

    @TableField(value = "sat_duty_time")
    private String satDutyTime;

    @TableField(value = "sat_work_time")
    private String satWorkTime;

    @TableField(value = "status")
    private String status;

    @TableField(value = "sun_duty_time")
    private String sunDutyTime;

    @TableField(value = "sun_work_time")
    private String  sunWorkTime;

    @TableField(value = "operating_company")
    private String operatingCompany;

    @TableField(value = "operating_time")
    private String operatingTime;

    @TableField(value = "operator")
    private String operator;
}
