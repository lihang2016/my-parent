package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 17:27
 */
@Getter
@Setter
public class PaperCheckingDesDto extends BaseDto {

    private Long questionId;

    private Boolean itCorrect;

    private Integer whatQuestion;

}
