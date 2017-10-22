package com.my.biz.config;


import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.udc.UDC;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 启动监听
 * Created by 96230 on 2017/6/3.
 */
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BaseEntity.inited();
        UDC.initUdcService();
    }
}
