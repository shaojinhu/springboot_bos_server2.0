package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.FixedAreaMapper;
import com.bos.basic.service.FixedAreaService;
import com.bos.pojo.basic.FixedArea;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class FixedAreaServiceImpl implements FixedAreaService {

    @Resource
    private FixedAreaMapper fixedAreaMapper;

    /**
     * 获得定区列表
     * @param map
     * @return
     */
    @Override
    public Result getFixedAreaList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));

        IPage<FixedArea> iPage = new Page<>(page,size);
        IPage<FixedArea> iPageFixedArea = fixedAreaMapper.selectPage(iPage, null);
        PageResult<FixedArea> pageResult = new PageResult<>(iPageFixedArea.getTotal(),iPageFixedArea.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 添加定区
     * @param fixedArea
     * @param map
     * @return
     */
    @Override
    public Result addFixedArea(FixedArea fixedArea, Map<String, String> map) {
        //设置操作人等信息
        fixedArea.setOperator(map.get("nikename"));
        fixedArea.setOperatingCompany(map.get("company"));
        fixedArea.setOperatingTime(map.get("operatingTime"));
        int insert = fixedAreaMapper.insert(fixedArea);
        if(insert >0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改定区
     * @param fixedArea
     * @param map
     * @return
     */
    @Override
    public Result updateFixedArea(FixedArea fixedArea, Map<String, String> map) {
        //设置操作人等信息
        fixedArea.setOperator(map.get("nikename"));
        fixedArea.setOperatingCompany(map.get("company"));
        fixedArea.setOperatingTime(map.get("operatingTime"));
        int i = fixedAreaMapper.updateById(fixedArea);
        if(i >0){
            return  Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除定区
     * @param fixedArea
     * @return
     */
    @Override
    public Result deleteFixedArea(FixedArea fixedArea) {
        //TODO
        // 判断是否存在分区，否则不允许删除
        return null;
    }

    /**
     * 获取全部的定区
     * @return
     */
    @Override
    public Result getFixeArea() {
        List<FixedArea> fixedAreas = fixedAreaMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,fixedAreas);
    }
}
