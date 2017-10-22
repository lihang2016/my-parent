package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 20:31
 */
@Getter
@Setter
public class QuestionsDto extends BaseDto {


    private String name;

    private String imgUrl;

    private Integer type;

    private  Integer paperCount;//考试题多少题

    private  Integer practiceCount;//练习题多少题

    private Long id;
}
