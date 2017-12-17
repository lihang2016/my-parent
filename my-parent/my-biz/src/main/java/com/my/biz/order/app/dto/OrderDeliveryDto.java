package com.my.biz.order.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:订单送货dto 入参
 * @Date 2017/11/26 14:05
 */
@Getter
@Setter
public class OrderDeliveryDto  extends BaseDto{
    @NotNull(message = "订单id不能为空")
    private Long id;

    @NotNull(message = "送货人不能为空")
    private String deliveryMan;

    @NotNull(message = "送货人电话不能为空")
    private String deliveryManPhone;
}
