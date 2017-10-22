package com.my.biz.advertisement.domain.repository;


import com.my.biz.advertisement.domain.entity.Advertisement;
import com.my.biz.common.entity.BaseRepository;

import java.util.List;

/**
 * Created by 96230 on 2017/6/10.
 */
public interface AdvertisementRepository extends BaseRepository<Advertisement,Long> {
    /**
     * 查询广告
     * @param positionId
     * @return
     */
    List<Advertisement> findByPositionId(Long positionId);

}
