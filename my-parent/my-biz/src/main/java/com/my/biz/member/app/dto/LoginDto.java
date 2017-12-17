package com.my.biz.member.app.dto;

import com.my.biz.common.interactive.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Created by 96230 on 2017/6/10.
 */
@Getter
@Setter
public class LoginDto extends BaseDto {
    @NotNull(message = "账号不能为空")
    private String account;
    @NotNull(message = "密码不能为空")
    private String password;

    public interface Update{

    }
}
