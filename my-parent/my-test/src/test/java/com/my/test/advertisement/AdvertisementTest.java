package com.my.test.advertisement;

import com.alibaba.fastjson.JSON;
import com.my.biz.advertisement.app.service.AdvertisementAppService;
import com.my.test.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description 广告测试案列
 * @Date 2017/7/28 23:47
 */
@Slf4j
public class AdvertisementTest extends TestBase {

    @Autowired
    private AdvertisementAppService advertisementAppService;

    @Test
    public void testFindAdvertisementByPositionId(){
         log.info(JSON.toJSONString(advertisementAppService.findByPositionId(1L).to()));
    }
}
