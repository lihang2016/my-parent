package com.my.web.common.webcommon.conaa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:配置PageRequest类型转换
 * @Date 2017/10/29 16:07
 */
@Configuration
public class PageRequestConfig extends WebMvcConfigurerAdapter {
    @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new PageResquestConverter());
	}
}
