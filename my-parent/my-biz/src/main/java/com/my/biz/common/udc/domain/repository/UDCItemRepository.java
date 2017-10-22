
package com.my.biz.common.udc.domain.repository;


import com.my.biz.common.entity.BaseRepository;
import com.my.biz.common.udc.domain.entity.UDCItem;

import java.util.List;

/**
 *
 */
public interface UDCItemRepository extends BaseRepository<UDCItem, Long> {


    List<UDCItem> findByTypeIdOrderByValue(Long typeId);



}
