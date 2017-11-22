package com.my;

import com.my.biz.config.ApplicationReadyListener;
import com.my.common.annotation.Boot;
import org.springframework.boot.SpringApplication;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description 程序入口
 * @Date 2017/7/8 12:07
 */
@Boot
public class Main {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.addListeners(new ApplicationReadyListener());
        springApplication.run(Main.class, args);
    }
}
