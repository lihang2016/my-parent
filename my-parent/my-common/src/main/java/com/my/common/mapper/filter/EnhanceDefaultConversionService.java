package com.my.common.mapper.filter;

import org.springframework.core.convert.support.DefaultConversionService;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 9:35 2017/10/27
 */
public class EnhanceDefaultConversionService  extends DefaultConversionService {
    public static EnhanceDefaultConversionService INSTANCE = new EnhanceDefaultConversionService();

    private EnhanceDefaultConversionService() {
        super();
        addConverter(new StringToDateConverter());
    }
}
