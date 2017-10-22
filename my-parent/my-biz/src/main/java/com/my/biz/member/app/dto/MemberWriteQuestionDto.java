package com.my.biz.member.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 17:04
 */
@Getter
@Setter
public class MemberWriteQuestionDto extends BaseDto {

    @NotNull
    List<MemberAnswerDto> memberAnswerDtoList;

    private Long time;
}
