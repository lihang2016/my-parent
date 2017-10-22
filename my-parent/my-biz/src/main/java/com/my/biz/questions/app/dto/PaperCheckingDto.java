package com.my.biz.questions.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 17:12
 */
@Getter
@Setter
public class PaperCheckingDto extends BaseDto {


    private Long paperId;
    /**
     * 正确
     */
    private Integer correct;

    /**
     * 及格分数
     */
    private Double passingMark;

    /**
     * 错误
     */
    private Integer error;

    /**
     * 未做
     */
    private Integer unfinished;

    private Double results;

    List<PaperCheckingDesDto> paperCheckingDesDtoList;
}
