package com.my.web.common.webcommon.filter;

/**
 * @Author:lihang
 * @Description:后台拦截器
 * @Date Create in 9:51 2017/7/25
 */
public class BackEndFilter {
    public  static CPACLFilter loginFilterRegistrationBean() {
        CPACLFilter bean = new CPACLFilter.Builder().loginUrl("/loginName.json")
                .notNeedLoginUrls("/front/static/assets/**","/front/product_detail.html","/front/static/css/**","/front/static/fonts/**","/front/static/img/**","/front/static/js/**")
                .urlPatterns("/backend/**").rememberMeUserUrls("/backend/testback.json").supportUserType("backend").build2();
        return bean;
    }
}
