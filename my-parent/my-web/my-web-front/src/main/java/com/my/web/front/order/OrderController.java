package com.my.web.front.order;

import com.my.biz.common.interactive.NULL;
import com.my.biz.common.interactive.PageRequest;
import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.order.app.dto.OrderDeliveryDto;
import com.my.biz.order.app.service.OrderAppService;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/24 20:41
 */
@RestController
public class OrderController extends FrontContrllor{

    @Autowired
    private OrderAppService orderAppService;

    /**
     * 订单列表查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/findPageOrder.json")
    public ViewInfo findPageOrder(PageRequest<NULL> request){
        return orderAppService.findPageOrder(request).to();
    }

    /**
     * 查询订单详情
     * @param modelId
     * @return
     */
    @RequestMapping(value = "/order/findById.json")
    public ViewInfo findById(ModelId modelId){
        return orderAppService.findById(modelId.getId()).to();
    }

    /**
     * 查询全部价格档次
     * @return
     */
    @RequestMapping(value = "/order/findPriceGradeAll.json")
    public ViewInfo findPriceGradeAll(){
        return orderAppService.findPriceGradeAll().to();
    }

    /**
     * 开始送货
     * @param orderDeliveryDto
     * @return
     */
    @RequestMapping(value = "/order/orderDelivery.json",method = RequestMethod.POST)
    public ViewInfo orderDelivery(OrderDeliveryDto orderDeliveryDto){
        return orderAppService.orderDelivery(orderDeliveryDto).to();
    }
}
