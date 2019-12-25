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
 * 定区实体类fixed_area
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fixed_area")
public class FixedArea implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;  //id

    @TableField(value = "fixed_area_name")
    private String fixedAreaName; //定区名称

    @TableField(value = "operating_company")
    private String operatingCompany; //操作部门

    @TableField(value = "operating_time")
    private String operatingTime; //操作时间

    @TableField(value = "operator")
    private String operator; //操作人

    @TableField(value = "area_id")
    private String areaID;//区域id

}
