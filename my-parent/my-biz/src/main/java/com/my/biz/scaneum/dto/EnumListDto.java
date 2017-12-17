/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/7/14 11:12 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class EnumListDto {
    /**
     * 枚举类名
     */
    private String simpleName;
    /**
     * 枚举类名对应的实例集合
     */
    private List<Enum> enumsList;
}
