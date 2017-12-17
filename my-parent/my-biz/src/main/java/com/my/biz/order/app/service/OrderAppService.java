package com.my.biz.order.app.service;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.entity.Pages;
import com.my.biz.common.interactive.CPResponse;
import com.my.biz.common.interactive.NULL;
import com.my.biz.common.interactive.PageRequest;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.order.app.dto.OrderDeliveryDto;
import com.my.biz.order.app.dto.OrderDetailsDto;
import com.my.biz.order.app.dto.OrderDto;
import com.my.biz.order.app.dto.OrderPriceGradeDto;
import com.my.biz.order.domain.service.OrderDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/24 20:32
 */
@AppService
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 查询订单列表 分页
     * @param pageRequest
     * @return
     */
    public SingleResponse<Page<OrderDto>> findPageOrder(PageRequest<NULL> pageRequest) {
        return SingleResponse.from(Pages.map(orderDomainService.findPageOrder(pageRequest.getMap(), pageRequest.getPageable()), OrderDto.class));
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public SingleResponse<OrderDetailsDto> findById(Long id){
        return SingleResponse.from(orderDomainService.findById(id).to(OrderDetailsDto.class));

    }


    /**
     * 查询全部价格档次
     * @return
     */
    public SingleResponse<List<OrderPriceGradeDto>> findPriceGradeAll(){
        return SingleResponse.from(BaseEntity.map(orderDomainService.findPriceGradeAll(), OrderPriceGradeDto.class));
    }

    /**
     * 开始送货
     * @param orderDeliveryDto
     * @return
     */
    public CPResponse orderDelivery(OrderDeliveryDto orderDeliveryDto){
        orderDomainService.orderDelivery(orderDeliveryDto);
        return new CPResponse();
    }
}
