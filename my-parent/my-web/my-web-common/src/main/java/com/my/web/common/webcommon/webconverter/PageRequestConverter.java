package com.my.web.common.webcommon.webconverter;


import com.google.common.collect.Maps;
import com.my.biz.common.interactive.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:自定义PageRequest 类型转换
 * @Date 2017/10/28 13:01
 */
@Component
public class PageRequestConverter extends AbstractArgumentResolver {

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
       return PageRequest.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, Object> pageRequestMap = Maps.newHashMap();
        Map<String, String> dtos = Maps.newHashMap();
        for (Map.Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
            if (entry.getKey().contains("_")) {
                if( entry.getValue()[0] instanceof String){
                    injectDetect(entry.getValue()[0]);
                }
                pageRequestMap.put(entry.getKey(), entry.getValue()[0]);
            } else {
                dtos.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        PageRequest pageRequest = new PageRequest();
        Pageable pageable = pageableHandlerMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest,
                binderFactory);
        org.springframework.data.domain.PageRequest pageRequest1 = new org.springframework.data.domain.PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        pageRequest.setPageable(pageRequest1);
        pageRequest.setMap(pageRequestMap);
        return pageRequest;
    }
}
