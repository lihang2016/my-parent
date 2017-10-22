package com.my.biz.common.udc.app.service;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.common.udc.app.dto.UDCItemDto;
import com.my.biz.common.udc.domain.entity.UDCItem;
import com.my.biz.common.udc.domain.service.UDCTypeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@Transactional
public class UDCTypeAppService {

    @Autowired
    private UDCTypeDomainService domainService;

    /**
     * 根据UDC类型取Item
     *
     * @param request typeId
     * @return SingleResponse<List<UDCItemDto>>
     */
    @Transactional(readOnly = true)
    public SingleResponse<List<UDCItemDto>> findAll(Long request) {
        List<UDCItem> udcItems = domainService.getByTypeId(request);
        return SingleResponse.from(BaseEntity.map(udcItems, UDCItemDto.class));
    }

    @Transactional(readOnly = true)
    public SingleResponse<List<UDCItemDto>> findByTypeCode(String request) {
        List<UDCItem> udcItems = domainService.findByTypeCode(request);
        return SingleResponse.from(BaseEntity.map(udcItems, UDCItemDto.class));
    }
}
