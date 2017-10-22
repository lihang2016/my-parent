package com.my.biz.subject.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 14:54
 */
@Getter
@Setter
public class SubjectDetailsDto extends BaseDto {

    private String imgUrl;

    private String name;

    private String author;

    private Integer viewingTimes;

    private Boolean fee=Boolean.FALSE;

    private BigDecimal money;

    private String des;

    private List<SubjectClassDto> classList;

    private Date uploadTime;

    private Integer subjectType;

    private Integer subjectChildrenType;


    private  Integer classCount;//总课时
}
