package com.my.common.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.*;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/10/29 16:03
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
@MapperScan(basePackages = "com.my.biz.*.domain.repository")
public @interface Boot {
}
