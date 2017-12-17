package com.my.biz.commodity.domain.service;

import com.my.biz.commodity.app.dto.CommodityCUDto;
import com.my.biz.commodity.domain.entity.Commodity;
import com.my.biz.commodity.domain.entity.CommodityType;
import com.my.biz.commodity.domain.repository.CommodityRepository;
import com.my.biz.commodity.domain.repository.CommodityTypeRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:35
 */
@DomainService
public class CommodityDomainService {

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private CommodityTypeRepository commodityTypeRepository;
    /**
     * 商品列表
     * @param map
     * @param pageable
     * @return
     */
    public Page<Commodity> findCommodityPage(Map<String,Object> map, Pageable pageable){
        return   commodityRepository.findAllPage(map,pageable);
    }

    /**
     * 查询分类
     * @param id
     * @return
     */
    public List<CommodityType> findCommodityType(Long id){
            if(id==-1) return commodityTypeRepository.find("NULL_parentId",id);
            return commodityTypeRepository.find("EQ_parentId",id);
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public Commodity findById(Long id){
        if(id==null) CPBusinessException.throwIt("id 不能为空");
        Commodity commodity=commodityRepository.get(id);
        if(commodity==null){
            CPBusinessException.throwIt("不存在该商品");
        }
        return commodity;
    }

    /**
     * 新增商品
     * @param dto
     */
    public void create(CommodityCUDto dto){
       Commodity commodity=new Commodity();
       commodity.from(dto);
       commodityRepository.create(commodity);
    }

    /**
     * 修改商品
     * @param dto
     */
    public void update(CommodityCUDto dto){
        if(dto.getId()==null) CPBusinessException.throwIt("修改时候商品id不能为空");
        Commodity commodity=commodityRepository.get(dto.getId());
        if(commodity==null) CPBusinessException.throwIt("该订单不存在");
        commodity.from(dto);
        commodityRepository.update(commodity);

    }
}
