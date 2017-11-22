/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/7/14 9:16 创建
 */
/*
 * @author Administrator
 */
package com.my.biz.scaneum.listener;

import com.my.biz.scaneum.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class MeataApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired private MetaDataService metaDataService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        metaDataService.scanAllEnum();
        metaDataService.scanFlowEntity();
    }
}
