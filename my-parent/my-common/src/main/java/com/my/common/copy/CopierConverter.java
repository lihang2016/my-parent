package com.my.common.copy;

import org.springframework.cglib.core.Converter;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/6 15:43
 */
public class  CopierConverter implements Converter {
    // value 源对象属性，target 目标对象属性类，aClass 目标对象o1方法名
    @Override
    public Object convert(Object value, Class aClass, Object o1) {
        if(value instanceof String){
            System.out.println("aaaaa");
        }
        if(value instanceof List){
            List list= (List) value;
            for(Object o:list){
                return null;
            }
        }
        return null;
    }
}
