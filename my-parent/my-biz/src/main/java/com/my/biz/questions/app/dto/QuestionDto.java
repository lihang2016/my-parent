package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:题dto
 * @Date 2017/8/5 16:28
 */
@Getter
@Setter
public class QuestionDto extends BaseDto {
    private String questionName;

    private Boolean multiselect; //1 false 不是 0 是 true

    private Integer whatQuestion;

    private Long id;

    private List<QunestionSelectDto> qunestionSelects;
}
