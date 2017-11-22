package com.my.web.front.questions;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.questions.app.dto.PaperSearchDto;
import com.my.biz.questions.app.dto.QuestionsSearchDto;
import com.my.biz.questions.app.service.QuestionsAppService;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:题库view
 * @Date 2017/7/31 20:33
 */
@RestController
public class QuestionsContrllor extends FrontContrllor {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private QuestionsAppService questionsAppService;

    /**
     * 查询题库列表
     * @param questionsSearchDto
     * @return
     */
    @RequestMapping("/questions/find.json")
    public ViewInfo find(QuestionsSearchDto questionsSearchDto){
            return questionsAppService.find(questionsSearchDto).convertTo();
    }

    /**
     * 查询全部试卷，或者某题库中的试卷
     * @param paperSearchDto
     * @return
     */
    @RequestMapping("/questions/findPaper.json")
    public ViewInfo findPaper(PaperSearchDto paperSearchDto){
        return questionsAppService.findPaper(paperSearchDto).convertTo();
    }

    /**
     * 查询全部题
     * @param modelId
     * @return
     */
    @RequestMapping("/questions/findPagerId.json")
    public ViewInfo findByPaperId(ModelId modelId){
        return questionsAppService.findQuestionList(modelId.getId()).convertTo();
    }

}
