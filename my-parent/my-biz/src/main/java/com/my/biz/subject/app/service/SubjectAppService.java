package com.my.biz.subject.app.service;

import com.my.biz.common.entity.Pages;
import com.my.biz.common.interactive.SingleResponse;
import com.my.biz.subject.app.dto.SubjectDetailsDto;
import com.my.biz.subject.app.dto.SubjectListDto;
import com.my.biz.subject.app.dto.SubjectSearchDto;
import com.my.biz.subject.domain.service.SubjectDomainService;
import com.my.common.annotation.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 12:10
 */
@AppService
@Transactional
public class SubjectAppService {

    @Autowired
    private SubjectDomainService subjectDomainService;


    /**
     * 查询课程列表---前台
     * @param subjectSearchDto
     * @return
     */
    @Transactional(readOnly = true)
    public SingleResponse<Page<SubjectListDto>> findSubjectList(SubjectSearchDto subjectSearchDto){
      return   SingleResponse.from(Pages.map(subjectDomainService.findSubjectList(subjectSearchDto),SubjectListDto.class));
    }

    /**|
     * 根据课程id 查询课程详情----前端
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public SingleResponse<SubjectDetailsDto> findById(Long id){
        return SingleResponse.from(subjectDomainService.findById(id).to(SubjectDetailsDto.class));
    }
}
