package com.my.biz.subject.app.dto;

import com.my.biz.common.interactive.PageDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 12:02
 */
@Getter
@Setter
public class SubjectSearchDto extends PageDto {

    private  Integer subjectType;//课程大类型

    private Integer subjectChildrenType;//课程小类型

    private Boolean newSubject=false;//是否最新

    private Boolean recommend=false; //是否推荐

}
