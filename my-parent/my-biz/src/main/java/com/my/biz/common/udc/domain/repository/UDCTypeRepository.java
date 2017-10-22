
package com.my.biz.common.udc.domain.repository;


import com.my.biz.common.entity.BaseRepository;
import com.my.biz.common.udc.domain.entity.UDCType;

/**
 *
 */
public interface UDCTypeRepository extends BaseRepository<UDCType, Long> {
    UDCType findByCode(String code);

}
