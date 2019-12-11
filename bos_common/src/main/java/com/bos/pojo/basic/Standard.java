package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * 收派标准Standard
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "standard")
public class Standard implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;  //id

    @TableField(value = "name")
    private String name;  //标准名称

    @TableField(value = "min_weight")
    private Integer minWeight;  //最小重量

    @TableField(value = "max_weight")
    private Integer maxWeight;  //最大重量

    @TableField(value = "min_length")
    private Integer minLength;  //最小长度

    @TableField(value = "max_length")
    private Integer maxLength;  //最大长度

    @TableField(value = "operating_company")
    private String operatingCompany; //操作单位

    @TableField(value = "operating_time")
    private String operatingTime;  //操作时间

    @TableField(value = "operator")
    private String operator;  //操作人

}
