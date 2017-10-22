package com.my.test.type;

import com.alibaba.fastjson.JSON;
import com.my.biz.common.udc.UDC;
import com.my.biz.type.app.dto.AllTypeSearchDto;
import com.my.biz.type.app.service.AllTypeAppService;
import com.my.test.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:33
 */
@Slf4j
public class AllTypeTest extends TestBase {

    @Autowired
    private AllTypeAppService allTypeAppService;

    @Test
    public void findByType(){
        AllTypeSearchDto allTypeSearchDto=new AllTypeSearchDto();
        allTypeSearchDto.setType(UDC.newUDCWithItemCode("allType","paper"));
      log.info(JSON.toJSONString(allTypeAppService.findAllType(allTypeSearchDto).convertTo()));
    }

    @Test
    public void testFindByTypeId(){
        log.info(JSON.toJSONString(allTypeAppService.findAllTypeById(1L).convertTo()));
    }
}
