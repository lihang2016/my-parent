package com.my.biz.common.interactive;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/10/28 12:53
 */
@Getter
@Setter
public class PageRequest<T extends NULL> extends BaseDto {
    Map<String,Object> map;

    Pageable pageable;
}
