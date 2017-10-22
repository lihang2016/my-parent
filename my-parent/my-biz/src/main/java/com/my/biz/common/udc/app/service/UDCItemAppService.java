package com.my.biz.common.udc.app.service;

import com.my.biz.common.entity.Pages;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.common.udc.app.dto.UDCItemDto;
import com.my.biz.common.udc.domain.entity.UDCItem;
import com.my.biz.common.udc.domain.service.UDCItemDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
@Service
public class UDCItemAppService {

    @Autowired
    private UDCItemDomainService domainService;

    @Transactional(readOnly = true)
    public SingleResponse<UDCItemDto> findById(Long request) {
        return SingleResponse.from(domainService.findById(request).to(UDCItemDto.class));
    }

    @Transactional(readOnly = true)
    public SingleResponse<Page<UDCItemDto>> findAll(UDCItemDto request) {
        Page<UDCItem> UDCItem = domainService.findAll(request);
        return SingleResponse.from(Pages.map(UDCItem, UDCItemDto.class));
    }
}
