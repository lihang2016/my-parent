package com.my.biz.advertisement.app.service;

import com.my.biz.advertisement.app.dto.AdvertisementDto;
import com.my.biz.advertisement.domain.service.AdvertisementDomainService;
import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.interactive.SingleResponse;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 96230 on 2017/6/10.
 */
@AppService
@Transactional
public class AdvertisementAppService {

    @Autowired
    private AdvertisementDomainService advertisementDomainService;

    /**tggyyyyyyyyyyyyyyy
     * 查询广告
     * @param positionId
     * @return
     */
    public SingleResponse<List<AdvertisementDto>> findByPositionId(Long positionId){
        return SingleResponse.from(BaseEntity.map(advertisementDomainService.findByPositionId(positionId),AdvertisementDto.class));
    }
}
