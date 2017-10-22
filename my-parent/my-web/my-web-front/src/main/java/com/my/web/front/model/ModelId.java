package com.my.web.front.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/29 0:06
 */
@Getter
@Setter
public class ModelId {
    @NotNull(message = "id 不能为空")
    private Long id;
}
