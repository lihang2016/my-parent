package com.my;

import com.my.biz.config.ApplicationReadyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description 程序入口
 * @Date 2017/7/8 12:07
 */
@SpringBootApplication
//@ServletComponentScan()
public class Main {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Main.class);
        springApplication.addListeners(new ApplicationReadyListener());
        springApplication.run(Main.class, args);
    }
}
