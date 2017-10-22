package com.my.biz.subject.app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:课程列表Dto
 * @Date 2017/7/29 12:04
 */
@Getter
@Setter
public class SubjectListDto {

    private String imgUrl;

    private String name;

    private String author;

    private Integer viewingTimes;

    private Integer classCount;
}
