package com.my.biz.type.app.dto;

import com.my.biz.common.interactive.BaseDto;
import com.my.biz.common.udc.UDC;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:26
 */
@Getter
@Setter
public class AllTypeSearchDto extends BaseDto {
    private UDC type;
}
