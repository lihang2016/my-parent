package com.my.web.front.udc;

import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.common.udc.app.dto.UDCItemDto;
import com.my.biz.common.udc.app.service.UDCTypeAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:lihang
 * @Description:查询码表
 * @Date Create in 20:24 2017/7/6
 */
@RestController
public class UDCController {

    @Autowired
    private UDCTypeAppService udcTypeAppService;

    @GetMapping("/udc/list.json")
    public ViewInfo findUDC(@RequestParam("typeCode")String typeCode){

        SingleResponse<List<UDCItemDto>> list=udcTypeAppService.findByTypeCode(typeCode);
      return  list.convertTo();
    }
}
