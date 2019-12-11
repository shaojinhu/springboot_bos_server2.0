package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 父档案
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@TableName("parent_archive")
public class ParentArchive implements Serializable {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(value = "archive_name")
    private String archiveName;

    @TableField(value = "archive_num")
    private String archiveNum;

    @TableField(value = "has_child")
    private String hasChild;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "operating_company")
    private String operatingCompany;

    @TableField(value = "operating_time")
    private String operatingTime;

    @TableField(value = "operator")
    private String operator;
}
