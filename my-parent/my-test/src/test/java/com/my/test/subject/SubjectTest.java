package com.my.test.subject;

import com.alibaba.fastjson.JSON;
import com.my.biz.subject.app.dto.SubjectSearchDto;
import com.my.biz.subject.app.service.SubjectAppService;
import com.my.test.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 13:09
 */
@Slf4j
public class SubjectTest extends TestBase {

    @Autowired
    private SubjectAppService subjectAppService;

    @Test
    public void testSubjectList(){
        SubjectSearchDto subjectSearchDto=new SubjectSearchDto();
        subjectSearchDto.setSubjectChildrenType(1);
        log.info(JSON.toJSONString(subjectAppService.findSubjectList(subjectSearchDto).convertTo()));
    }

    @Test
    public void testFindById(){
        log.info(JSON.toJSONString(subjectAppService.findById(1L).convertTo()));
    }
}
