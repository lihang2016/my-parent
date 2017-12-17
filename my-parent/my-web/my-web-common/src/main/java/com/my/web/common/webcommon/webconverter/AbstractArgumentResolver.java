package com.my.web.common.webcommon.webconverter;

import com.my.common.exception.CPBusinessException;
import com.my.common.utils.Null;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 16:17 2017/12/11
 */
@Slf4j
public abstract class AbstractArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    protected ConversionService conversionService;

    protected Object handleDto(MethodParameter parameter, Map<String, String> dtos) {
        dtos.remove("page");
        dtos.remove("size");
        dtos.remove("sort");
        Class genricType = getGenricType(parameter);
        if (genricType != null) {
            String data = dtos.get("data");
            if (ClassUtils.isPrimitiveOrWrapper(genricType)) {
                return conversionService.convert(data, genricType);
            }
            if (String.class.isAssignableFrom(genricType)) {
                return data;
            }
            return getBeanWrapper(genricType, dtos);
        }
        return Null.INSTANCE;
    }

    protected Class getGenricType(MethodParameter parameter) {
        ParameterizedType genericParameterType = (ParameterizedType) parameter.getGenericParameterType();
        if (genericParameterType != null) {
            Type[] actualTypeArguments = genericParameterType.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                return (Class) actualTypeArguments[0];
            }
        }
        return null;
    }
    private Object getBeanWrapper(Class genricType, Map<String, String> dtos) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(genricType);
        beanWrapper.setConversionService(conversionService);
        for (Map.Entry<String, String> entry : dtos.entrySet()) {
            try {
                beanWrapper.setPropertyValue(entry.getKey(), entry.getValue());
            } catch (NotWritablePropertyException e) {
                log.error("设置bean属性值失败:{}", e);
            }
        }
        return beanWrapper.getWrappedInstance();
    }

    /**
     * 检测和防止sql注入,匹配了;和--,''
     * @param value
     */
    protected   static void injectDetect(String value){
        if(value == null || value.length() == 0)return;
        Pattern pattern=Pattern.compile(".*([';]+|(--)+).*");
        Matcher matcher=pattern.matcher(value);
        while (matcher.find()){
            CPBusinessException.throwIt("查询中不能包括--;''等特殊字符");
        }
    }
}
