package com.bos.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.pojo.basic.TakeTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TakeTimeMapper extends BaseMapper<TakeTime> {

    //获取收派时间管理列表
//    @Select("SELECT  d.dname AS companyName,t.`id` AS id ,t.`name` AS NAME ,t.`normal_duty_time` AS normalDutyTime " +
//            ",t.`normal_work_time` AS normalWorkTime,t.`sat_duty_time` AS satDutyTime,t.`sat_work_time` AS satWorkTime, " +
//            "t.`status` AS STATUS ,t.`sun_duty_time` AS sunDutyTime,t.`sun_work_time` AS sunWorkTime, " +
//            "t.`operating_company` AS operatingCompany ,t.`operating_time` AS operatingTime , " +
//            "t.`operator` AS operator FROM `take_time` t,`depa` d WHERE d.`did` = t.`company_id`")
//    List<TakeTimeVo> getTakeTimeList(Page<TakeTimeVo> page);
}
