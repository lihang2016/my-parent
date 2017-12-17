package com.my.web.front.commodity;

import com.my.biz.commodity.app.dto.CommodityCUDto;
import com.my.biz.commodity.app.service.CommodityAppService;
import com.my.biz.common.interactive.NULL;
import com.my.biz.common.interactive.PageRequest;
import com.my.biz.common.interactive.ViewInfo;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:52
 */
@RestController
public class CommodityController  extends FrontContrllor{
    @Autowired
    private CommodityAppService commodityAppService;


    /**
     * 查询商品列表
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "/commodity/findCommodityPage.json")
    public ViewInfo findCommodityPage(PageRequest<NULL> pageRequest){
        return commodityAppService.findCommodityPage(pageRequest).to();
    }

    /**
     * 查询分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/commodity/findCommodityType.json")
    public ViewInfo findCommodityType(Long id){
        return commodityAppService.findCommodityType(id).to();
    }

    /**
     * 新增商品
     * @param dto
     * @return
     */
    @RequestMapping(value = "/commodity/create.json",method = RequestMethod.POST)
    public ViewInfo create(@RequestBody CommodityCUDto dto){
        return commodityAppService.create(dto).to();
    }

    /**
     * 修改商品
     * @param dto
     * @return
     */
    @RequestMapping(value = "/commodity/update.json",method = RequestMethod.POST)
    public ViewInfo update(CommodityCUDto dto){
        return commodityAppService.update(dto).to();
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/commodity/findById.json")
    public ViewInfo findById(ModelId id){
        return commodityAppService.findById(id.getId()).to();
    }
}
