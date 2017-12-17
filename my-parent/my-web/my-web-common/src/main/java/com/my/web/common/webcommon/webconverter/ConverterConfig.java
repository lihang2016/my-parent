package com.my.web.common.webcommon.webconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description:web配置
 * @Date 2017/10/29 16:07
 */
@Configuration
public class ConverterConfig extends WebMvcConfigurerAdapter {
	/**
	 * 配置pageRequest,ListRequest
	 * @param argumentResolvers
	 */
    @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(pageableHandlerMethodArgumentResolver());
		argumentResolvers.add(pageRequestArgumentResolver());
		argumentResolvers.add(listRequestConverter());
	}

	/**
	 * web 使用jackson
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter.getClass().toString().endsWith("MappingJackson2HttpMessageConverter")) {
				MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter= (MappingJackson2HttpMessageConverter) converter;
				ObjectMapper objectMapper=mappingJackson2HttpMessageConverter.getObjectMapper();
				objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8")); //解决jackson 使用国际标准时间GMT进行格式化,导致和国内时区相差8小时的bug
			}
		}
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
		PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver(
				sortHandlerMethodArgumentResolver());
		PageRequest pageRequest = new PageRequest(0, DEFAULT_PAGE_SIZE);
		pageableHandlerMethodArgumentResolver.setFallbackPageable(pageRequest);
		return pageableHandlerMethodArgumentResolver;
	}

	@Bean
	public PageRequestConverter pageRequestArgumentResolver() {
		return new PageRequestConverter();
	}


	@Bean
	public ListRequestConverter listRequestConverter(){
		return new ListRequestConverter();
	}
	@Bean
	public SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver() {
		return new CustomSortHandlerMethodArgumentResolver();
	}
}
