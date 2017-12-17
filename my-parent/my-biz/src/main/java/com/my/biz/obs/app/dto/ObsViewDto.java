package com.my.biz.obs.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 18:01 2017/12/13
 */
@Getter
@Setter
public class ObsViewDto extends BaseDto {
    private String viewUrl;
    private String downloadUrl;
    private Long id;
}
