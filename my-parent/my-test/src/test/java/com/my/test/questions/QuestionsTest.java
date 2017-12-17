package com.my.test.questions;

import com.alibaba.fastjson.JSON;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.questions.app.dto.PaperSearchDto;
import com.my.biz.questions.app.dto.QuestionDto;
import com.my.biz.questions.app.dto.QuestionsSearchDto;
import com.my.biz.questions.app.service.QuestionsAppService;
import com.my.test.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:35
 */
@Slf4j
public class QuestionsTest extends TestBase {

    @Autowired
    private QuestionsAppService questionsAppService;

    @Test
    public void testFind(){
        QuestionsSearchDto questionsSearchDto=new QuestionsSearchDto();
        log.info(JSON.toJSONString(questionsAppService.find(questionsSearchDto).to()));
    }

    @Test
    public void testFindPaper(){
        PaperSearchDto paperSearchDto=new PaperSearchDto();
        log.info(JSON.toJSONString(questionsAppService.findPaper(paperSearchDto).to()));
    }

    @Test
    public void testFindPaperId(){
        SingleResponse<List<QuestionDto>> singleResponse=questionsAppService.findQuestionList(1L);
        ViewInfo viewInfo=singleResponse.to();
        log.info(JSON.toJSONString(viewInfo));
    }
}
