package com.my.biz.member.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 17:07
 */
@Getter
@Setter
public class MemberTestDto extends BaseDto {

    private Long paperId;

    private Long id;

    private Double results;

    private Integer writeCount;

    private Double passingMark;

    private Long time;

}
