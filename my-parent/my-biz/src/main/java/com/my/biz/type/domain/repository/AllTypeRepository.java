package com.my.biz.type.domain.repository;

import com.my.biz.common.entity.BaseRepository;
import com.my.biz.common.udc.UDC;
import com.my.biz.type.domain.entity.AllType;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:20
 */
public interface AllTypeRepository extends BaseRepository<AllType,Long> {
    List<AllType> findByType(UDC type);

    List<AllType> findByTypeId(Long id);
}
