package com.my.biz.order.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/25 21:49
 */
@Getter
@Setter
public class OrderDetailsListDto extends BaseDto {
    private Long orderId;

    private Long barCode;

    private String name;

    private Integer number;

    private BigDecimal supplierPrice;

    private  BigDecimal shopPrice;
}
