package com.my.biz.obs.app.dto;

import com.my.biz.common.interactive.CPResponse;
import com.my.common.exception.CPBusinessException;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:12 2017/12/13
 */
@Getter
@Setter
public class ObsResult  extends CPResponse {

    private Long id;
    private byte[] content;
    private String fileName;

    private long fileSize;
    public InputStream getInputStream() {
       if(this.content==null){
           CPBusinessException.throwIt("io异常");
       }
        return new ByteArrayInputStream(this.content);
    }
}
