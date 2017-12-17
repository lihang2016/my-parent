package com.my.biz.order.domain.service;

import com.my.biz.order.app.dto.OrderDeliveryDto;
import com.my.biz.order.domain.entity.Order;
import com.my.biz.order.domain.entity.OrderPriceGrade;
import com.my.biz.order.domain.repository.OrderPriceGradeRepository;
import com.my.biz.order.domain.repository.OrderRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
/**
 * @Author lihang 【962309372@qq.com】
 * @Description:订单领域
 * @Date 2017/11/24 20:31
 */
@DomainService
public class OrderDomainService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPriceGradeRepository orderPriceGradeRepository;

    /**
     * 条件订单查询
     * @param map
     * @param pageable
     * @return
     */
    public Page<Order> findPageOrder(Map<String,Object> map, Pageable pageable){
        return orderRepository.findAllPage(map,pageable);
    }



    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public Order findById(Long id){
        return orderRepository.get(id);
    }

    /**
     * 查询全部价位档次
     * @return
     */
    public List<OrderPriceGrade> findPriceGradeAll(){
        return orderPriceGradeRepository.getAll();
    }

    /**
     * 订单送货
     * @param deliveryDto
     */
    public void orderDelivery(OrderDeliveryDto deliveryDto){
        Order order=findById(deliveryDto.getId());
        if(order==null){
            CPBusinessException.throwIt("该订单不存在");
        }
        order.from(deliveryDto);
        orderRepository.update(order);
    }
}
