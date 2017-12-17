package com.my.biz.order.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 13:55
 */
@Getter
@Setter
public class OrderPriceGradeDto extends BaseDto {
    private String grade;

    private BigDecimal money;
}
