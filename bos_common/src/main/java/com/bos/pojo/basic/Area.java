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
 * 区域实体类Area
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "area")
public class Area implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id; //id

    @TableField(value = "city")
    private String city; //市

    @TableField(value = "city_code")
    private String cityCode; //城市编码

    @TableField(value = "district")
    private String district; //区/县

    @TableField(value = "post_code")
    private String postCode; //邮编

    @TableField(value = "province")
    private String province; //省

    @TableField(value = "brevity_code")
    private String brevityCode; //简码
}
