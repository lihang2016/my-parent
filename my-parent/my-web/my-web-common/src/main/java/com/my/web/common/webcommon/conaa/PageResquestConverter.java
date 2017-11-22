package com.my.web.common.webcommon.conaa;


import com.google.common.collect.Maps;
import com.my.biz.common.interactive.PageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:自定义PageRequest 类型转换
 * @Date 2017/10/28 13:01
 */

public class PageResquestConverter implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType().isAssignableFrom(PageRequest.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Map<String, String[]> map1 = nativeWebRequest.getParameterMap();
        Map<String, Object> map = Maps.newHashMap();
        PageRequest pageRequest = new PageRequest();
        Integer size=10;
        Integer page=0;
        for (String in : map1.keySet()) {
            if("size".equals(in)){
                size=Integer.parseInt(map1.get(in)[0]);
            }else
            if("page".equals(in)) page=Integer.parseInt(map1.get(in)[0])==1?0:Integer.parseInt(map1.get(in)[0]);
            else
            map.put(in,map1.get(in)[0]);//填充map
        }
        pageRequest.setMap(map);
        Pageable pageable = new org.springframework.data.domain.PageRequest(page,size);
        pageRequest.setPageable(pageable);
        return pageRequest;
    }
}
