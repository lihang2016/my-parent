package com.my.biz.advertisement.domain.service;


import com.my.biz.advertisement.domain.entity.Advertisement;
import com.my.biz.advertisement.domain.repository.AdvertisementRepository;
import com.my.biz.subject.app.service.SubjectAppService;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 会员领域服务
 * Created by 96230 on 2017/6/10.
 */
@DomainService
public class AdvertisementDomainService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private SubjectAppService subjectAppService;
    /**
     * 查询广告
     * @param positionId
     * @return
     */
    public List<Advertisement> findByPositionId(Long  positionId){
        List<Advertisement> advertisement=advertisementRepository.findByPositionId(positionId);
        if(advertisement==null && advertisement.size()<=0){
            CPBusinessException.throwIt("positionId 不存在");
        }
        return advertisement;
    }

}
