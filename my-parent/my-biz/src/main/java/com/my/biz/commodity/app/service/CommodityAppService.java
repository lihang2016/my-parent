package com.my.biz.commodity.app.service;

import com.my.biz.commodity.app.dto.CommodityCUDto;
import com.my.biz.commodity.app.dto.CommodityDto;
import com.my.biz.commodity.app.dto.CommodityTypeDto;
import com.my.biz.commodity.domain.service.CommodityDomainService;
import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.entity.Pages;
import com.my.biz.common.interactive.CPResponse;
import com.my.biz.common.interactive.NULL;
import com.my.biz.common.interactive.PageRequest;
import com.my.biz.common.interactive.SingleResponse;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:38
 */
@AppService
public class CommodityAppService {

    @Autowired
    private CommodityDomainService commodityDomainService;

    /**
     * 查询商品列表
     * @param pageRequest
     * @return
     */
    public SingleResponse<Page<CommodityDto>> findCommodityPage(PageRequest<NULL> pageRequest){
        return SingleResponse.from(Pages.map(commodityDomainService.findCommodityPage(pageRequest.getMap(),pageRequest.getPageable()),CommodityDto.class));
    }

    /**
     * 查询全部分类
     * @param id
     * @return
     */
    public SingleResponse<List<CommodityTypeDto>> findCommodityType(Long id){
        return SingleResponse.from(BaseEntity.map(commodityDomainService.findCommodityType(id),CommodityTypeDto.class));
    }

    /**
     * 新增商品
     * @param dto
     * @return
     */
    public CPResponse create(CommodityCUDto dto){
        commodityDomainService.create(dto);
        return new CPResponse();
    }

    /**
     * 修改商品
     * @param dto
     * @return
     */
    public CPResponse update(CommodityCUDto dto){
        commodityDomainService.update(dto);
        return  new CPResponse();
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public SingleResponse<CommodityDto> findById(Long id){
        return SingleResponse.from(commodityDomainService.findById(id).to(CommodityDto.class));
    }
}
