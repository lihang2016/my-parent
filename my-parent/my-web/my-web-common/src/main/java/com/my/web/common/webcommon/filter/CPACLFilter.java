/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-26 16:35 创建
 */
package com.my.web.common.webcommon.filter;

import com.my.biz.member.app.dto.MemberDto;
import com.my.common.session.MemberUser;
import com.my.common.session.MyContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class CPACLFilter extends OncePerRequestFilter {
    protected String loginUrl;
    protected String[] notNeedLoginUrls = null;

    protected String[] rememberMes=null;

    protected AntPathMatcher antPathMatcher = new AntPathMatcher();
    protected String[] supportUserType;
    protected String homeUrl;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException,
            IOException {

        String uri = getRequestURI(request);
        if (notNeedLogin(uri)) {
            filterChain.doFilter(request, response);
        } else {
            //超时判断
//            if (checkSessionTimeout(request, response)) return;
            //类型判断
            if (checkUserType(request, response)) return;
                //认证判断
                if (!SecurityUtils.getSubject().isAuthenticated()) {
                    response.setStatus(707);
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print("not logged");
                    return;
                } else {
                    MemberDto memberDto= (MemberDto) SecurityUtils.getSubject().getPrincipal();
                    MemberUser memberUser=new MemberUser();
                    memberUser.setId(memberDto.getId());
                    MyContext.set(memberUser);
                }
            filterChain.doFilter(request, response);
        }

    }


    /**
     * 记住我判断
     * @param uri
     * @return
     */
    protected  boolean checkRememberMe(String uri){
        if(!SecurityUtils.getSubject().isRemembered()){
            return  false;
        }
        if(rememberMes!=null)
        for (int i = 0; i < rememberMes.length; i++) {
            if (antPathMatcher.match(rememberMes[i], uri)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 超时判断
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
//
//    protected boolean checkSessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//            if(isWechatAccess(request)){
//               response.sendRedirect(response.encodeRedirectURL(loginUrl));
//                return true ;
//            }
//            //如果session超时，并且是ajax请求
//            if(request.getRequestURI().endsWith(".json")){
//                response.setStatus(707);
//                response.setCharacterEncoding("utf-8");
//                response.getWriter().print("session timeout");
//                return true;
//            }
//        return false;
//    }

    protected boolean checkUserType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Session session=SecurityUtils.getSubject().getSession();
        if (null != session) {
            MemberDto memberDto= (MemberDto) SecurityUtils.getSubject().getPrincipal();
            if(memberDto!=null){
                if (memberDto != null) {
                    boolean support = false;
                    String[] supportTypes = getSupportUserType();
                    for(String type : supportTypes){
                        if(type.equalsIgnoreCase(memberDto.getMemberType())){
                            support = true;
                            break;
                        }
                    }
                    if (!support) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setCharacterEncoding("utf-8");
                        response.getWriter().print("Illegal access");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    protected boolean isWechatAccess(HttpServletRequest request) {
        return request.getHeader("User-Agent").indexOf("MicroMessenger") != -1;
    }
    public void init() {
        Assert.hasText(loginUrl);
        if (notNeedLoginUrls == null) {
            notNeedLoginUrls = new String[1];
            notNeedLoginUrls[0] = loginUrl;
        } else {
            String[] tmp = new String[notNeedLoginUrls.length + 1];
            System.arraycopy(notNeedLoginUrls, 0, tmp, 0, notNeedLoginUrls.length);
            tmp[tmp.length - 1] = loginUrl;
            notNeedLoginUrls = tmp;
        }
    }

    protected boolean notNeedLogin(String uri) {
        for (int i = 0; i < notNeedLoginUrls.length; i++) {
            if (antPathMatcher.match(notNeedLoginUrls[i], uri)) {
                return true;
            }
        }
        return false;
    }

    protected boolean rememberMe(String uri) {
        for (int i = 0; i < notNeedLoginUrls.length; i++) {
            if (antPathMatcher.match(notNeedLoginUrls[i], uri)) {
                return true;
            }
        }
        return false;
    }

    protected String getRequestURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int idx = uri.indexOf(';');
        if (idx > -1) {
            uri = uri.substring(0, idx);
        }
        return uri;
    }
    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String[] getNotNeedLoginUrls() {
        return notNeedLoginUrls;
    }

    public String[] getSupportUserType() {
        return supportUserType;
    }

    public void setSupportUserType(String... supportUserType) {
        this.supportUserType = supportUserType;
    }

    public void setNotNeedLoginUrls(String... notNeedLoginUrls) {
        this.notNeedLoginUrls = notNeedLoginUrls;
    }

    public String[] getRememberMes() {
        return rememberMes;
    }

    public void setRememberMes(String[] rememberMes) {
        this.rememberMes = rememberMes;
    }

    public static class Builder {
        private String loginUrl;
        private String homeUrl;
        private String[] notNeedLoginUrls = null;
        private String[] urlPatterns = null;

        private String[] rememberMes=null;

        protected String[] supportUserType;

        private CPACLFilter filter = null;

         public Builder() {
        }
         public Builder filter(CPACLFilter filter) {
             this.filter = filter;
            return this;
        }

        /**
         * 设置登录页面url
         */
        public Builder loginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
            return this;
        }
          /**
         * 设置登录页面url
         */
        public Builder homeUrl(String homeUrl) {
            this.homeUrl = homeUrl;
            return this;
        }
        /**
         * 设置不需要登录的url，支持ant表达式
         */
        public Builder notNeedLoginUrls(String... notNeedLoginUrls) {
            this.notNeedLoginUrls = notNeedLoginUrls;
            return this;
        }
        /**
         * 允许何种类型的用户访问
         */
        public Builder supportUserType(String... supportUserType) {
            this.supportUserType = supportUserType;
            return this;
        }

        /**
         * 允许记住我类型的用户访问
         */

        public Builder rememberMeUserUrls(String... rememberMeUserUrl){
            this.rememberMes=rememberMeUserUrl;
            return this;
        }

        /**
         * 设置此filter生效的路径
         */
        public Builder urlPatterns(String... urlPattern) {
            this.urlPatterns = urlPattern;
            return this;
        }

//        public FilterRegistrationBean build() {
//            Assert.notEmpty(urlPatterns);
//            Assert.notNull(supportUserType);
//
//            CPACLFilter filter = this.filter;
//            if (filter == null) {
//                filter = new CPACLFilter();
//            }
//            filter.setLoginUrl(loginUrl);
//            filter.setHomeUrl(homeUrl);
//            filter.setNotNeedLoginUrls(notNeedLoginUrls);
//            filter.setSupportUserType(supportUserType);
//            filter.init();
//            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//            filterRegistrationBean.setFilter(filter);
//            filterRegistrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
//            filterRegistrationBean.setUrlPatterns(Lists.newArrayList(urlPatterns));
//            return filterRegistrationBean;
//        }

        public CPACLFilter build2() {
            CPACLFilter filter = new CPACLFilter();
            filter.setLoginUrl(loginUrl);
            filter.setHomeUrl(homeUrl);
            filter.setNotNeedLoginUrls(notNeedLoginUrls);
            filter.setSupportUserType(supportUserType);
            filter.setRememberMes(rememberMes);
            filter.init();
            return filter;
        }
    }
}
