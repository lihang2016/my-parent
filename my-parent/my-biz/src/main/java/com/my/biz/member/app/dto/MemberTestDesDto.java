package com.my.biz.member.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/8/5 23:01
 */
@Getter
@Setter
public class MemberTestDesDto  extends BaseDto{

    private Integer correct;

    private Integer error;

    private Integer unfinished;

    private Double results;

    List<MemberTestWriteDesDto> memberTestDes;
}
