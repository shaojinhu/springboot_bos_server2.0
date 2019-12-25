package com.bos.basic.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.pojo.basic.Courier;
import com.bos.vo.CourierVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

@Mapper
public interface CourierMapper extends BaseMapper<Courier> {

    //查询快递员列表返回Vo类List
    @Select("SELECT c.id AS id,c.`courier_num` AS courierNum,c.`name` AS NAME,c.`check_pwd` AS checkPwd, " +
            "c.`telephone`,c.`company_name` AS companyName,c.`deltag`,c.`type`,c.`vehicle_type` AS vehicleType,c.`vehicle_num` AS vehicleNum, " +
            "c.`vehicle_type` AS cehicleType,c.`pdanum`,s.`max_length` AS maxLength,s.`max_weight` AS maxWeight, " +
            "s.`min_length` AS minLength,s.`min_weight` AS minWeight,t.`normal_duty_time` AS normalDutyTime, " +
            "t.`normal_work_time` AS normalWorkTime,t.`sat_duty_time` AS satDutyTime,t.`sun_work_time` AS sunWorkTime, " +
            "t.`sat_work_time` AS satWorkTime,t.`sun_duty_time` AS sunDutyTime,c.`standard_id` AS standardId,c.`taketime_id` AS takeTimeId " +
            "FROM `courier` AS c INNER JOIN `standard` AS s ON c.`standard_id` = s.`id` " +
            "INNER JOIN `take_time` AS t ON c.`taketime_id` = t.`id` " +
            " ${ew.customSqlSegment} ")
    List<CourierVo> getCourierList(Page<CourierVo> page, @Param(Constants.WRAPPER)QueryWrapper<Courier> queryWrapper);


    //批量修改
    @Update(" <script> " +

            " update " +
            "  courier " +
            " <set>" +
            " <if test='taketimeId != null'>`taketime_id` = #{taketimeId},</if> " +
            " <if test='standardId != null'>`standard_id` = #{standardId},</if> " +
            " </set>" +
            " where " +
            " `id` in "+
            " <foreach collection = 'list' item ='item' open='(' close=')' separator=','> "+
            "  #{item.id} " +
            " </foreach> " +
            " </script> ")
    void updateByID(@Param("list")List<Courier> couriers,@Param("standardId")String standardId,@Param("taketimeId")String taketimeId);


}
