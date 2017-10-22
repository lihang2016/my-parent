
package com.my.biz.common.udc.biz;


import com.my.biz.common.udc.domain.entity.UDCType;

/**
 *
 */
public interface UDCService {
    UDCType findByCode(String code);
}
