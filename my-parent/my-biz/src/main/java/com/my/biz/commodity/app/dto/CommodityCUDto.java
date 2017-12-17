package com.my.biz.commodity.app.dto;

import com.my.biz.commodity.domain.enums.CompanyEnum;
import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:59
 */
@Getter
@Setter
public class CommodityCUDto extends BaseDto {

    private Long id;
    @NotNull
    private Long code;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private CompanyEnum companyEnum;
    @NotNull
    private Long oneType;
    @NotNull
    private Long twoType;
    @NotNull
    private Long threeType;
    @NotNull
    private BigDecimal supplierOneLevel;
    @NotNull
    private BigDecimal supplierTwoLevel;
    @NotNull
    private BigDecimal supplierThreeLevel;
    @NotNull
    private BigDecimal supplierFourLevel;
}
