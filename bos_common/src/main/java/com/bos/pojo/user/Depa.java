package com.bos.pojo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "depa")
@TableName(value = "depa")
public class Depa implements Serializable {

    @Id
    @TableId(value = "did")
    private String did;

    @Column(name = "dname")
    @TableField(value = "dname")
    private String dname;

}
