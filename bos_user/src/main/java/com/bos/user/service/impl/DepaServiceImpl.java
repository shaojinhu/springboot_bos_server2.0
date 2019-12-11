package com.bos.user.service.impl;

import com.bos.pojo.user.Depa;
import com.bos.response.Result;
import com.bos.response.ResultCode;
import com.bos.user.repository.DepaRepository;
import com.bos.user.service.DepaService;
import com.bos.util.IdWorker;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DepaServiceImpl implements DepaService {

    @Resource
    private DepaRepository depaRepository;
    @Resource
    private IdWorker idWorker;

    /**
     * 获得全部的部门
     * @return
     */
    @Override
    public Result getDepa(){
        List<Depa> all = depaRepository.findAll();
        return new Result(ResultCode.SUCCESS,all);
    }

    /**
     * 添加Depa部门
     * @param depa
     * @return
     */
    @Override
    public Result addDepa(Depa depa) {
        try {
            String id = idWorker.nextId()+"";
            depa.setDid(id);
            depaRepository.save(depa);
            return Result.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAIL();
        }
    }

    /**
     * 修改部门Depa
     * @param depa
     * @return
     */
    @Override
    public Result updateDepa(Depa depa) {
        try {
            Depa target = depaRepository.findById(depa.getDid()).get();
            BeanUtils.copyProperties(depa,target);
            depaRepository.save(target);
            return Result.SUCCESS();
        } catch (BeansException e) {
            e.printStackTrace();
            return Result.FAIL();
        }
    }

    /**
     * 删除Depa部门
     * @param depa
     * @return
     */
    @Override
    public Result deleteDepa(Depa depa) {
        //首先查看该部门下是否存在用户，存在用户则不让删除
        Integer userByDepaId = depaRepository.getUserByDepaId(depa.getDid());
        if(userByDepaId > 0) {
            return new Result(ResultCode.DEPA_HAVE_USER);
        }else{
            //执行删除
            depaRepository.delete(depa);
            return Result.SUCCESS();
        }
    }
}
