package com.my.biz.obs.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:17 2017/12/13
 */
@Getter
@Setter
public class ObsDto  extends BaseDto {

    private String fileName;

    private Long fileSize;

    private String providerId;

    private InputStream inputStream;
}
