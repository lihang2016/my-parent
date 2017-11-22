/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/4/19 14:32 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldInfoDto {
    //字段名
    private String fieldName;
    //字段中文名
    private String displayName;
    //字段类型
    private String fieldType;

    //分组名
    private String groupName;
    //方案id
    private  Long schemeId;
}
