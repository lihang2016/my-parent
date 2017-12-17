package com.my.web.common.webcommon.filter;

/**
 * @Author:lihang
 * @Description:
 * @Date Create in 2:12 2017/7/21
 */
public class FrontFilter {

    public  static CPACLFilter loginFilterRegistrationBean() {
        CPACLFilter bean = new CPACLFilter.Builder().loginUrl("/front/member/login.json")
                .notNeedLoginUrls("/front/static/assets/**","/front/product_detail.html","/front/static/css/**","/front/static/fonts/**","/front/static/img/**","/front/static/js/**")
                .urlPatterns("/front/**").supportUserType("front").build2();
        return bean;
    }
}
