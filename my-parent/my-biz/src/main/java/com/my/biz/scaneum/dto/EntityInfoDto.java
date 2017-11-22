/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/5/4 15:57 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityInfoDto {
    //实体类全名 如：com.zds.boot.metadata.entity.Scheme
    private  String entityFullName;
    //实体类中文名
    private  String entityDisplayName;
    //实体类所属模块名
    private  String  entityModuleName;
}
