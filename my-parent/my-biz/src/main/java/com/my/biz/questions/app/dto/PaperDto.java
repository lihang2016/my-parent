package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:00
 */
@Getter
@Setter
public class PaperDto extends BaseDto {
    /**
     * 试卷名称
     */
    private String name;

    /**\
     * 练习题还是考试题
     */
    private Integer type;

    /**
     * 题库id
     */
    private Long questionsId;

    private Long id;

    /**
     * 一共多少题
     */
    private Integer countQuestion;

    /**
     * 考试时间
     */
    private Long examinationTime;

}
