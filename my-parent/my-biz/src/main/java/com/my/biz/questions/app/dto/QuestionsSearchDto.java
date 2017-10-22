package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.PageDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:27
 */
@Getter
@Setter
public class QuestionsSearchDto extends PageDto {

    /**
     * 题库名称
     */
    private String name;

}
