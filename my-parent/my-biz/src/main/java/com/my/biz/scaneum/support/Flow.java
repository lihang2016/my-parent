/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/5/4 16:01 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Flow {
     //类中文名
    String displayName() default "";
     //类所属模块名
    String moduleName() default "";
}
