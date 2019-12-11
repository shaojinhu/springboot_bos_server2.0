package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.CourierMapper;
import com.bos.basic.mapper.StandardMapper;
import com.bos.basic.service.StandardService;
import com.bos.pojo.basic.Courier;
import com.bos.pojo.basic.Standard;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@SuppressWarnings("all")
public class StandardServiceImpl implements StandardService {

    @Resource
    private StandardMapper standardMapper;
    @Resource
    private CourierMapper courierMapper;

    /**
     * 获取取派标准Standard的列表
     * @param map
     * @return
     */
    @Override
    public Result getStandardList(Map<String, Object> map) {
        //解析参数
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        //或许还会有根据取派标准名称进行模糊查询
        QueryWrapper<Standard> queryWrapper = new QueryWrapper<>();
//        if(!StringUtils.isEmpty(map.get("name").toString())){//不为空
//            queryWrapper.like("name",map.get("name").toString());
//        }
        //分页构造器
        IPage<Standard> iPage = new Page<>(page,size);
        IPage<Standard> iPageResult = standardMapper.selectPage(iPage, queryWrapper);
        PageResult<Standard> pageResult = new PageResult<>(iPageResult.getTotal(),iPageResult.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 添加Standard取派标准
     * @param standard
     * @param map
     * @return
     */
    @Override
    public Result addStandard(Standard standard, Map<String, String> map) {
        //设置操作人信息
        standard.setOperator(map.get("nikename"));
        standard.setOperatingCompany(map.get("company"));
        standard.setOperatingTime(map.get("operatingTime"));
        int insert = standardMapper.insert(standard);
        if(insert > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改Standard取派标准
     * @param standard
     * @param map
     * @return
     */
    @Override
    public Result updateStandard(Standard standard, Map<String, String> map) {
        //设置操作人信息
        standard.setOperator(map.get("nikename"));
        standard.setOperatingCompany(map.get("company"));
        standard.setOperatingTime(map.get("operatingTime"));
        int i = standardMapper.updateById(standard);
        if(i >0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除Stanard取派标准
     * @param standard
     * @return
     */
    @Override
    public Result deleteStandard(Standard standard) {
        //如有快递员正在使用，则不允许删除
        QueryWrapper<Courier> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("standard_id",standard.getId());
        Integer integer = courierMapper.selectCount(queryWrapper);
        if(integer > 0){
            return new Result(ResultCode.STANDARD_USERING);
        }else{
            //删除
            int i = standardMapper.deleteById(standard.getId());
            if(i > 0){
                return  Result.SUCCESS();
            }else{
                return Result.FAIL();
            }
        }
    }

    /**
     * 获取全部的Standard
     * @return
     */
    @Override
    public Result getStandard() {
        List<Standard> standards = standardMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,standards);
    }


}
