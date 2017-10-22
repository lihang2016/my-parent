package com.my.web.front.subject;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.subject.app.dto.SubjectSearchDto;
import com.my.biz.subject.app.service.SubjectAppService;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 12:15
 */
@RestController
public class SubjectContrllor extends FrontContrllor{

    @Autowired
    private SubjectAppService subjectAppService;

    /**
     * 查询课程列表
     * @param subjectSearchDto
     * @return
     */
    @RequestMapping("/subject/subjectList.json")
    public ViewInfo findSubjectList(SubjectSearchDto subjectSearchDto){
        return subjectAppService.findSubjectList(subjectSearchDto).convertTo();
    }

    @RequestMapping("/subject/subjectDetails.json")
    public ViewInfo findSubjectDetails(ModelId modelId){
        return  subjectAppService.findById(modelId.getId()).convertTo();
    }
}
