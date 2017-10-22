package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.PageDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:56
 */
@Getter
@Setter
public class PaperSearchDto extends PageDto {
    private Long questionsId;

    /**
     * 试卷大类型
     */
    private Integer maxType;

    /**
     * 试卷小类型
     */
    private Integer minType;
}
