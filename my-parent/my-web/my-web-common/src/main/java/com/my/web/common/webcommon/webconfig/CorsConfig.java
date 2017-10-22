package com.my.web.common.webcommon.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/front/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(1728000);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
////        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
//        InterceptorRegistration addInterceptor = registry.addInterceptor(new LoginInterceptor());
//        addInterceptor.excludePathPatterns("/loginName.json");
//        addInterceptor.addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
}
