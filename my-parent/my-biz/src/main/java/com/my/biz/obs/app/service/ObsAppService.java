package com.my.biz.obs.app.service;

import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.obs.app.dto.ObsDto;
import com.my.biz.obs.domain.service.ObsDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:26 2017/12/13
 */
@AppService
@Transactional
public class ObsAppService {

    @Autowired
    private ObsDomainService obsDomainService;

    /**
     * 创建obs 返回id
     * @param obsDto
     * @return
     */
    public SingleResponse<Long> put(ObsDto obsDto){
        return SingleResponse.from(obsDomainService.doPut(obsDto));
    }

    /**
     * 获取
     * @param id
     * @return
     */
    public SingleResponse<ObsDto> get(Long id){
        return SingleResponse.from(obsDomainService.findById(id),ObsDto.class);
    }
}
