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
 * 分区实体类sub_area
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sub_area")
public class SubArea implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "assist_key_word")
    private String assistKeyWord;  // 分区辅助关键字

    @TableField(value = "key_word")
    private String keyWord; //分区关键字

    @TableField(value = "single")
    private String single; //单双号码

    @TableField(value = "start_num")
    private String startNum; //起始号码

    @TableField(value = "end_num")
    private String endNum; //终止号码

    @TableField(value = "area_id")
    private String areaId; //区域id

    @TableField(value = "fixed_area_id")
    private String fixedAreaId;  //定区id

    @TableField(value = "sub_area_leader")
    private String subAreaLeader;  //分区负责人

    @TableField(value = "telephone")
    private String telephone;  //分区负责人电话号码

    @TableField(value = "company")
    private String company;  //分区负责人部门
}
