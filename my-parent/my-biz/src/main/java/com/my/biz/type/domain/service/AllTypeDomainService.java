package com.my.biz.type.domain.service;

import com.my.biz.type.app.dto.AllTypeSearchDto;
import com.my.biz.type.domain.entity.AllType;
import com.my.biz.type.domain.repository.AllTypeRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:20
 */
@DomainService
public class AllTypeDomainService  {

    @Autowired
    private AllTypeRepository allTypeRepository;

    /**
     * 查询全部大类想
     * @param type
     * @return
     */
    public List<AllType> findByType(AllTypeSearchDto type){
        List<AllType> list=allTypeRepository.findByType(type.getType());
        if(list==null || list.size()<=0){
            CPBusinessException.throwIt("该类型不存在");
        }
        return list;
    }

    /**
     * 查询子类型
     * @param id
     * @return
     */
    public List<AllType> findByTypeId(Long id){
        if(id==null)CPBusinessException.throwIt("类型id 不能为空");
        List<AllType> list=allTypeRepository.findByTypeId(id);
        if(list==null || list.size()<=0){
            CPBusinessException.throwIt("该类型不存在");
        }
        return list;
    }
}
