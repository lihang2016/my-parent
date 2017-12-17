package com.my.biz.order.app.dto;

import com.my.biz.common.interactive.BaseDto;
import com.my.biz.order.domain.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:订单dto
 * @Date 2017/11/24 20:34
 */
@Getter
@Setter
public class OrderDto extends BaseDto {

    private Long id;
    private String orderNo;


    private Date orderTime;

    private String orderShop;

    private String shopAddress;

    private String shopPhone;

    private OrderStatusEnum orderStatusEnum;
}
