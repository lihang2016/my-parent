

package com.my.biz.common.udc.domain.service;

import com.my.biz.common.udc.domain.entity.UDCItem;
import com.my.biz.common.udc.domain.repository.UDCItemRepository;
import com.my.biz.common.udc.domain.repository.UDCTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class UDCTypeDomainService {

    @Autowired
    private UDCTypeRepository udcTypeRepository;

    @Autowired
    private UDCItemRepository udcItemRepository;


/*    public UDCType findById(Long id) {
        return udcTypeRepository.findOne(id);
    }

    public Page<UDCType> findAll(UDCTypeDto dto) {
        return udcTypeRepository.findAll(dto.toPage());
    }*/

    public List<UDCItem> getByTypeId(Long typeId) {
        return udcItemRepository.findByTypeIdOrderByValue(typeId);

    }

    public List<UDCItem> findByTypeCode(String code) {
        return udcTypeRepository.findByCode(code).getItems();
    }


}
