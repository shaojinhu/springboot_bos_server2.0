package com.bos.pojo.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分区实体类sub_area
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sub_area")
@Table(name = "sub_area")
@Entity
public class SubArea implements Serializable {

    @Id
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @Column(name = "assist_key_word")
    @TableField(value = "assist_key_word")
    private String assistKeyWord;  // 分区辅助关键字

    @Column(name = "key_word")
    @TableField(value = "key_word")
    private String keyWord; //分区关键字

    @Column(name = "single")
    @TableField(value = "single")
    private String single; //单双号码

    @Column(name = "start_num")
    @TableField(value = "start_num")
    private String startNum; //起始号码

    @Column(name = "end_num")
    @TableField(value = "end_num")
    private String endNum; //终止号码

    @Column(name = "area_id")
    @TableField(value = "area_id")
    private String areaId; //区域id

    @Column(name = "fixed_area_id")
    @TableField(value = "fixed_area_id")
    private String fixedAreaId;  //定区id

    @Column(name = "sub_area_leader")
    @TableField(value = "sub_area_leader")
    private String subAreaLeader;  //分区负责人

    @Column(name = "telephone")
    @TableField(value = "telephone")
    private String telephone;  //分区负责人电话号码

    @Column(name = "company")
    @TableField(value = "company")
    private String company;  //分区负责人部门

    @TableField(exist = false)
    @JoinTable( name = "sub_area_courier",joinColumns = @JoinColumn(name = "sub_area_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "courier_id",referencedColumnName = "id"))
    @ManyToMany
    private List<Courier> couriers = new ArrayList<>();

    //快递员id字符串，用于映射分区关联快递员表
    @TableField(exist = false)
    @Transient
    private String courierIds;
}
