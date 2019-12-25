package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 快递员Courier
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "courier")
@Entity
@Table(name = "courier")
public class Courier implements Serializable {

    @Id
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @Column(name = "courier_num")
    @TableField(value = "courier_num")
    private String courierNum;

    @Column(name = "name")
    @TableField(value = "name")
    private String name;

    @Column(name = "check_pwd")
    @TableField(value = "check_pwd")
    private String checkPwd;

    @Column(name = "telephone")
    @TableField(value = "telephone")
    private String telephone;

    @Column(name = "company_name")
    @TableField(value = "company_name")
    private String companyName;

    @Column(name = "deltag")
    @TableField(value = "deltag")
    private String deltag;

    @Column(name = "type")
    @TableField(value = "type")
    private String type;

    @Column(name = "vehicle_type")
    @TableField(value = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_num")
    @TableField(value = "vehicle_num")
    private String vehicleNum;

    @Column(name = "standard_id")
    @TableField(value = "standard_id")
    private String standardId;

    @Column(name = "taketime_id")
    @TableField(value = "taketime_id")
    private String takeTimeId;

//    @TableField(value = "vehicle_id")
//    private String vehicleId;

    @Column(name = "pdanum")
    @TableField(value = "pdanum")
    private String pdaNum;

}
