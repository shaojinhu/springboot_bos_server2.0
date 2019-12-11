package com.bos.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bos.basic.mapper.AreaMapper;
import com.bos.basic.service.AreaService;
import com.bos.pojo.basic.Area;
import com.bos.response.PageResult;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.util.PinYinUtil;
import com.bos.util.PostCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    /**
     * 获得区域列表，有分页
     * @param map
     * @return
     */
    @Override
    public Result getAreaList(Map<String, String> map) {
        Integer page = Integer.parseInt(map.get("page"));
        Integer size = Integer.parseInt(map.get("size"));
        IPage<Area> iPage = new Page<>(page,size);
        IPage<Area> iPageArea = areaMapper.selectPage(iPage, null);
        PageResult<Area> pageResult = new PageResult<>(iPageArea.getTotal(),iPageArea.getRecords());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 获得全部的Area，无分页
     * @return
     */
    @Override
    public Result getAreaAll() {
        List<Area> areas = areaMapper.selectList(null);
        return new Result(ResultCode.SUCCESS,areas);
    }

    /**
     * 添加area
     * @param area
     * @return
     */
    @Override
    public Result addArea(Area area) {
        //已存在的则不允许重复添加，根据城市编码查询是否存在重复的数据
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",area.getCityCode());
        Integer integer = areaMapper.selectCount(queryWrapper);
        if(integer > 0){
            return new Result(ResultCode.CITY_IS_EXIT);
        }
        String address = area.getProvince() + area.getCity() + area.getDistrict();
        //设置简码
        String s = PinYinUtil.toFirstChar(address).toUpperCase();
        Integer num =  (int)((Math.random()*9+1)*100000);
        area.setBrevityCode(s.concat(num.toString()));
        //设置邮编
        //String postCodeByAddr = PostCodeUtil.getPostCodeByAddr(address);
        //area.setPostCode(postCodeByAddr);
        System.out.println(area);
        int insert = areaMapper.insert(area);
        if(insert > 0){
            return  Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 修改Area
     * @param area
     * @return
     */
    @Override
    public Result updateArea(Area area) {
        //如果换了城市，那么就需要重新生成城市简码
        String address = area.getProvince() + area.getCity() + area.getDistrict();
        //设置简码
        String s = PinYinUtil.toFirstChar(address).toUpperCase();
        Integer num =  (int)((Math.random()*9+1)*100000);
        area.setBrevityCode(s.concat(num.toString()));
        int i = areaMapper.updateById(area);
        if(i > 0){
            return Result.SUCCESS();
        }
        return Result.FAIL();
    }

    /**
     * 删除Area
     * @param area
     * @return
     */
    @Override
    public Result deleteArea(Area area) {
        //TODO
        // 无定去使用时可删除
        return null;
    }
}
