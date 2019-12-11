package com.bos.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 快递员CourierVo类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierVo implements Serializable {

    //-----------------------快递员
    private String id;

    private String courierNum;

    private String name;

    private String checkPwd;

    private String telephone;

    private String companyName;

    private String deltag;

    private String type;

    private String vehicleNum;

    private String vehicleType;

    private String standardId;

    private String takeTimeId;

    private String pdaNum;

    //----------------------收派标准
    private String standardName;  //标准名称

    private Integer minWeight;  //最小重量

    private Integer maxWeight;  //最大重量

    private Integer minLength;  //最小长度

    private Integer maxLength;  //最大长度

    //-----------------取派时间
    private String normalDutyTime;

    private String normalWorkTime;

    private String satDutyTime;

    private String satWorkTime;

    private String sunDutyTime;

    private String  sunWorkTime;
}
