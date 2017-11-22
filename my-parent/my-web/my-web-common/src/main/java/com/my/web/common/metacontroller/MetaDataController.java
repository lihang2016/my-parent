/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/4/20 16:15 创建
*/
/*
 * @author Administrator
*/
package com.my.web.common.metacontroller;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.scaneum.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metadata")
public class MetaDataController {
    @Autowired
    private MetaDataService metaDataService;
//    @Autowired
//    private SchemeService schemeService;

    /**
     * http://127.0.0.1:8082/metadata/getSchemeList.json
     * 传参格式:schemeIds=1,2,3
     * @param schemeIds
     * @return
     */
//    @RequestMapping(value = "/getSchemeList.json",method = RequestMethod.GET)
//    public ViewResult getSchemeList(String schemeIds) {
//        return schemeService.getSchemeList(ListRequest.from(Arrays.asList(schemeIds.split(",")))).to();
//    }

    /**
     * 获取单个实体元数据信息
     * http://127.0.0.1:8082/metadata/getEntityMetaData.json
     * 传参格式:className=com.zds.boot.test.entity.City
     * @param classSimpleName
     * @return
     */
     @RequestMapping(value = "/getEntityMetaData.json",method = RequestMethod.GET)
    public ViewInfo getEntityMetaData(String classSimpleName) {
         return ViewInfo.from(metaDataService.getEntityMetaDataByName(classSimpleName));
    }

    /**
     * @return  工作流相关模块的模块名set
     */
    @RequestMapping(value = "/getFlowModuleList.json",method = RequestMethod.GET)
    public ViewInfo getFlowModuleList() {
         return ViewInfo.from(metaDataService.getFlowModuleList());
    }

    /**
     * 返回工作流模块对应的表单
     * @param moduleName
     * @return
     */
    @RequestMapping(value = "/findEntityByModuleName.json")
    public ViewInfo findEntityByModuleName(String moduleName) {
         return ViewInfo.from(metaDataService.getEntityInfoByModuleName(moduleName));
    }

    /**
     * 根据单个枚举类名,获取该枚举的所有实例
     * @param enumClassSimpleName 例： CustomerType
     * @return
     */
    @RequestMapping(value = "/getByEnumClassSimpleName.json")
    public ViewInfo getByEnumClassSimpleName(String enumClassSimpleName) {
         return ViewInfo.from(metaDataService.getByEnumClassSimpleName(enumClassSimpleName));
    }
     /**
     * 根据多个枚举类名,获取枚举的所有实例
     * @param simpleNames
     */
    @RequestMapping(value = "/getEnumsBySimpleNames.json")
    public ViewInfo getEnumsBySimpleNames(String simpleNames) {
         return ViewInfo.from(metaDataService.getByEnumClassSimpleName(Lists.newArrayList(Splitter.on(",").trimResults().splitToList(simpleNames))));
    }
}
