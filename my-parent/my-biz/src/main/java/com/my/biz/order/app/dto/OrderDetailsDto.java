package com.my.biz.order.app.dto;

import com.my.biz.common.interactive.BaseDto;
import com.my.biz.order.domain.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/25 21:48
 */
@Getter
@Setter
public class OrderDetailsDto extends BaseDto {
    private String orderNo;

    private Date orderTime;

    private String orderShop;

    private String shopAddress;

    private String shopPhone;

    private OrderStatusEnum orderStatusEnum;

    private List<OrderDetailsListDto> detailsList;
}
