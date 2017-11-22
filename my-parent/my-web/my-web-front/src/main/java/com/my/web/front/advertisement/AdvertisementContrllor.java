package com.my.web.front.advertisement;

import com.my.biz.advertisement.app.service.AdvertisementAppService;
import com.my.biz.common.interactive.ViewInfo;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description 广告view
 * @Date 2017/7/28 23:44
 */
@RestController
public class AdvertisementContrllor  extends FrontContrllor{

    @Autowired
    private AdvertisementAppService advertisementAppService;

    /**
     * 查询广告列表
     * @param modelId
     * @return
     */
    @GetMapping("/advertisement/findByPositionId")
    public ViewInfo findAdvertisementByPositionId(Long modelId){
        return advertisementAppService.findByPositionId(modelId).convertTo();
    }

}
