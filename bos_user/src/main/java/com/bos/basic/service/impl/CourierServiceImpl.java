package com.bos.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bos.basic.mapper.CourierMapper;
import com.bos.basic.service.CourierService;
import com.bos.execption.MyException;
import com.bos.pojo.basic.Courier;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.util.PinYinUtil;
import com.bos.vo.CourierVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class CourierServiceImpl implements CourierService {

    @Resource
    private CourierMapper courierMapper;


    /**
     * 获得Courier列表,默认查询启用的快递员
     * @param map
     * @return
     */
    @Override
    public Result getCourierList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        Page<CourierVo> courierVoPage = new Page<>(page,size);
        //获取条件
        QueryWrapper<Courier> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(map.get("name"))){
            queryWrapper.like("c.name",map.get("name"));
        }
        if(!StringUtils.isEmpty(map.get("type"))){
            queryWrapper.eq("c.type",map.get("type"));
        }
        if(!StringUtils.isEmpty(map.get("deltag"))){
            if(map.get("deltag").equals("0") || map.get("deltag").equals("1")){
                queryWrapper.eq("c.`deltag`",map.get("deltag"));
            }
        }
        List<CourierVo> courierList = courierMapper.getCourierList(courierVoPage,queryWrapper);
        PageResult<CourierVo> courierVoPageResult = new PageResult<>(courierVoPage.getTotal(),courierList);
        return new Result(ResultCode.SUCCESS,courierVoPageResult);
    }

    /**
     * 添加Courier
     * @param courier
     * @return
     */
    @Override
    public Result addCourier(Courier courier) {
        //添加默认的快递员均为已经启用
        courier.setDeltag("0");
        //设置快递员编号首字母
        String s = PinYinUtil.toFirstChar(courier.getName()).toUpperCase();
        Integer num =  (int)((Math.random()*9+1)*100000);
        courier.setCourierNum(s.concat(num.toString()));
        int insert = courierMapper.insert(courier);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改Courier
     */
    @Override
    public Result updateCourier(Courier courier) {
        int i = courierMapper.updateById(courier);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 启禁快递员
     * @param courier
     * @return
     */
    @Override
    public Result revCourier(Courier courier) {
        //TODO
        // 删除快递员首先要移除与定区分区表的关联，然后再次删除快递员
        if(courier.getDeltag().equals("0")){
            courier.setDeltag("1");
        }else{
            courier.setDeltag("0");
        }
        int i = courierMapper.updateById(courier);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }


    /**
     * 批量修改Courier
     * @param map
     * @return
     */
    @Override
    public Result batchCourier(Map<String, Object> map) throws MyException {
        try {
            List<Courier> couriers = (List<Courier>) map.get("couriers");
            Map<String,String> quMap = (Map<String,String>)map.get("map");
            String standardId = null;
            String takeTimeId = null;
            if(!StringUtils.isEmpty(quMap.get("standardId"))){
                standardId = quMap.get("standardId");
            }
            if(!StringUtils.isEmpty(quMap.get("takeTimeId"))){
                takeTimeId = quMap.get("takeTimeId");
            }
            courierMapper.updateByID(couriers,standardId,takeTimeId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultCode.FAIL);
        }
        return Result.SUCCESS();
    }

    /**
     * 获取全部快递员id
     * @return
     */
    @Override
    public Result getCourierID() {
        List<Courier> couriers = courierMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,couriers);
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }


}
