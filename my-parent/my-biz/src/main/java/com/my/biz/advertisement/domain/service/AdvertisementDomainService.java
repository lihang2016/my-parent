package com.my.biz.advertisement.domain.service;


import com.my.biz.advertisement.domain.entity.Advertisement;
import com.my.biz.advertisement.domain.repository.AdvertisementDao;
import com.my.common.annotation.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 会员领域服务
 * Created by 96230 on 2017/6/10.
 */
@DomainService
public class AdvertisementDomainService {

    @Autowired
    private AdvertisementDao advertisementDao;

    /**
     * 查询广告
     * @param positionId
     * @return
     */
    public List<Advertisement> findByPositionId(Long  positionId){
        List<Advertisement> advertisement=advertisementDao.getAll();
        return advertisement;
    }

}
