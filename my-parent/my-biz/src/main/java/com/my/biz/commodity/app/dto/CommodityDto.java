package com.my.biz.commodity.app.dto;

import com.my.biz.commodity.domain.enums.CompanyEnum;
import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:38
 */
@Getter
@Setter
public class CommodityDto extends BaseDto {
    private Long id;
    private Long code;

    private String name;

    private BigDecimal price;

    private CompanyEnum companyEnum;

    private Long oneType;

    private Long twoType;

    private Long threeType;

    private BigDecimal supplierOneLevel;

    private BigDecimal supplierTwoLevel;

    private BigDecimal supplierThreeLevel;

    private BigDecimal supplierFourLevel;


    private String oneTypeName;

    private String twoTypeName;

    private String threeTypeName;

    private Date rawAddTime;
}
