/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/4/19 14:47 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
    //分组名称
    String groupName() default "";
    //方案id,数据库预置的方案
    long schemeId();
}
