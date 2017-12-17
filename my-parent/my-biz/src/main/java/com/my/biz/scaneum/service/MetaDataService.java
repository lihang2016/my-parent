/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/4/19 14:18 创建
*/
/*
 * @author Administrator
*/
package com.my.biz.scaneum.service;


import com.my.biz.scaneum.dto.EntityInfoDto;
import com.my.biz.scaneum.dto.EnumListDto;
import com.my.biz.scaneum.dto.FieldInfoDto;

import java.util.List;
import java.util.Set;

/**
 * @Author:lihang
 * @Description:枚举扫描类
 * @Date Create in 16:21 2017/10/30
 */
public interface MetaDataService {
     /**
     * 获取单个实体元数据信息
     * @param classSimpleName 类全路径
     * @return 实体字段的元数据信息
     */
    List<FieldInfoDto>  getEntityMetaDataByName(String classSimpleName);

    /**
     * @return 实体@Flow注解上标明的模块名set
     */
    Set<String> getFlowModuleList();

     /**
     * 根据moduleName返回相应实体元数据信息
     *
     * @param moduleName 实体@Flow注解上标明的模块名
     * @return
     */
    List<EntityInfoDto> getEntityInfoByModuleName(String moduleName);

    /**
     * 根据单个枚举类名,获取该枚举的所有实例
     *
     * @param classSimpleName 例： CustomerType
     * @return
     */
    List<Enum> getByEnumClassSimpleName(String classSimpleName);
    
    /**
     * 根据多个枚举类名,获取枚举的所有实例
     * @param simpleNameList
     * @return  EnumDto集合
     */
    List<EnumListDto>  getByEnumClassSimpleName(List<String> simpleNameList);

    /**
     *  扫描类修饰符为public的枚举类
     * @return
     */
     void scanAllEnum() ;

    /**
     * 扫描所有带@Flow注解的实体,并获取实体元数据信息
     * @return
     */
    void scanFlowEntity();


}
