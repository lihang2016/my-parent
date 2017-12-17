package com.my.web.common.webcommon.webconverter;

import com.google.common.collect.Maps;
import com.my.biz.common.interactive.ListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 17:19 2017/12/11
 */
@Component
public class ListRequestConverter extends AbstractArgumentResolver {
    @Autowired
    private SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
       return ListRequest.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Map<String, Object> pageRequestMap = Maps.newHashMap();
        Map<String, String> dtos = Maps.newHashMap();
        for (Map.Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
            if (entry.getKey().contains("_")) {
                if( entry.getValue()[0] instanceof String){
                    injectDetect( entry.getValue()[0]);
                }
                pageRequestMap.put(entry.getKey(), entry.getValue()[0]);
            } else {
                dtos.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        ListRequest listRequest = new ListRequest();
        Sort sort = sortHandlerMethodArgumentResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest,
                webDataBinderFactory);
        listRequest.setSort(sort);
        listRequest.setMap(pageRequestMap);
        return listRequest;
    }
}
