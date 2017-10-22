package com.my.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author:lihang
 * @Description:领域服务
 * @Date Create in 9:37 2017/7/4
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DomainService {
}
