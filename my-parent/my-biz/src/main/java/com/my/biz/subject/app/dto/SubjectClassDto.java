package com.my.biz.subject.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:课程列表
 * @Date 2017/8/5 15:16
 */
@Getter
@Setter
public class SubjectClassDto extends BaseDto {
    private Double periods;

    private Boolean fee=Boolean.FALSE;

    private String className;

    private Long subjectId;
}
