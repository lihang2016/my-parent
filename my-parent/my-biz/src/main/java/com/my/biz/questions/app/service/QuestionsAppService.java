package com.my.biz.questions.app.service;

import com.my.biz.common.entity.BaseEntity;
import com.my.biz.common.entity.Pages;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.member.app.dto.MemberAnswerDto;
import com.my.biz.questions.app.dto.*;
import com.my.biz.questions.domain.service.QuestionDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:30
 */
@AppService
public class QuestionsAppService {

    @Autowired
    private QuestionDomainService questionDomainService;

    /**
     * 查询题库列表----前端
     * @param questionsSearchDto
     * @return
     */
    public SingleResponse<Page<QuestionsDto>> find(QuestionsSearchDto questionsSearchDto){
        return SingleResponse.from(Pages.map(questionDomainService.find(questionsSearchDto),QuestionsDto.class));
    }

    /**
     * 查询全部试卷或者某题库的试卷-----前端
     * @param paperSearchDto
     * @return
     */
    public SingleResponse<Page<PaperDto>> findPaper(PaperSearchDto paperSearchDto){
        return SingleResponse.from(Pages.map(questionDomainService.findPaper(paperSearchDto),PaperDto.class));
    }

    /**
     * 查询试卷中全部题-----前端
     * @param paperId
     * @return     */
    public SingleResponse<List<QuestionDto>> findQuestionList(Long paperId){
        return SingleResponse.from(BaseEntity.map(questionDomainService.findByPaperId(paperId),QuestionDto.class));
    }

    /**
     * 得分----前端；
     * @param memberAnswerDtos
     * @return
     */
    public SingleResponse<PaperCheckingDto> paperCheckingDto(List<MemberAnswerDto> memberAnswerDtos){
        return SingleResponse.from(questionDomainService.checkPaper(memberAnswerDtos));
    }
}
