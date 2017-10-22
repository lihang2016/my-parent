package com.my.biz.type.app.service;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.type.app.dto.AllTypeDto;
import com.my.biz.type.app.dto.AllTypeSearchDto;
import com.my.biz.type.domain.service.AllTypeDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:27
 */
@AppService
@Transactional
public class AllTypeAppService  {

    @Autowired
    private AllTypeDomainService allTypeDomainService;

    /**
     * 查询类型----前端
     * @param allTypeSearchDto
     * @return
     */
    public SingleResponse<List<AllTypeDto>> findAllType(AllTypeSearchDto allTypeSearchDto){
        return SingleResponse.from(BaseEntity.map(allTypeDomainService.findByType(allTypeSearchDto),AllTypeDto.class));
    }

    /**
     * 根据大类型找小类型---前端
     * @param id
     * @return
     */
    public SingleResponse<List<AllTypeDto>> findAllTypeById(Long id){
        return SingleResponse.from(BaseEntity.map(allTypeDomainService.findByTypeId(id),AllTypeDto.class));
    }
}
