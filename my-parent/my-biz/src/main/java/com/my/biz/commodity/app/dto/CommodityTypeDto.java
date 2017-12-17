package com.my.biz.commodity.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:50
 */
@Getter
@Setter
public class CommodityTypeDto extends BaseDto {
    private Long id;
    private String name;

    private Long parentId;
}
