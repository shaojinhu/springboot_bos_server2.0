package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 子档案
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@TableName(value = "sub_archive")
public class SubArchive implements Serializable {

    @TableId(value = "sub_id",type = IdType.ID_WORKER_STR)
    private String subId;

    @TableField(value = "parentid")
    private String parentid;

    @TableField(value = "sub_archive_name")
    private String subArchiveName;

    @TableField(value = "mnemonic_code")
    private String mnemonicCode;

    @TableField(value = "seal")
    private String seal;

    @TableField(value = "sub_archive_info")
    private String subArchiveInfo;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "operating_company")
    private String operatingCompany;

    @TableField(value = "operating_time")
    private String operatingTime;

    @TableField(value = "operator")
    private String operator;
}
