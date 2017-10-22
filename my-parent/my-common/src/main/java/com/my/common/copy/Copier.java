package com.my.common.copy;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

/**
 * Created by 96230 on 2017/5/29.
 */
public class Copier {

    /**\
     *
     * @param objects1 源对象
     * @param objects2 目标对象
     * @param isToNull 是否忽略空
     * @param converter
     */
    public static void copyBean(Object objects1, Object objects2, Boolean isToNull, Converter converter){
        CopierConverter copierConverter=new CopierConverter();
        BeanCopier copy=BeanCopier.create(objects1.getClass(), objects2.getClass(), isToNull);
        copy.copy(objects1,objects2,copierConverter);
    }
}
