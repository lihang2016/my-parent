package com.my.common.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description 领域服务
 * @Date 2017/7/8 16:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface AppService {
}
