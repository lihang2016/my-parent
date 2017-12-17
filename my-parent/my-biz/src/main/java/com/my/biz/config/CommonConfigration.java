
package com.my.biz.config;

import ch.qos.logback.classic.pattern.DateConverter;
import com.alibaba.fastjson.parser.ParserConfig;
import com.google.common.base.Splitter;
import com.my.biz.common.event.EventBus;
import com.my.biz.common.event.EventHandler;
import com.my.biz.common.udc.StringToUDCConverter;
import com.my.biz.common.udc.UDC;
import com.my.biz.common.udc.UDCDeserializer;
import com.my.biz.scaneum.listener.MeataApplicationReadyListener;
import com.my.common.mapper.pages.PageObjectFactory;
import com.my.common.mapper.pages.PageObjectWrapperFactory;
import com.my.common.mapper.pages.PageableExecutorInterceptor;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 *配置事件类
 * 2017年7月5日20:51:46
 */
@Configuration
public class CommonConfigration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;
    @Bean
    public static BeanDefinitionRegistryPostProcessor cpValidatorBeanDefinitionRegistryPostProcessor() {
        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
                scanner.addIncludeFilter(new AssignableTypeFilter(EventHandler.class));
                scanner.scan(Splitter.on(",").splitToList("com.my.biz.*").toArray(new String[0]));
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }
        };
    }
    /**
     * 自定义分页插件配置
     */
    @PostConstruct
    public void addPageInterceptor() throws Exception {
        PageableExecutorInterceptor interceptor = new PageableExecutorInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
            sqlSessionFactory.getConfiguration().setObjectFactory(new PageObjectFactory());
            sqlSessionFactory.getConfiguration().setObjectWrapperFactory(new PageObjectWrapperFactory());
            sqlSessionFactory.getConfiguration().setUseGeneratedKeys(true);
        }
    }

    @Bean
    public ApplicationReadyListener applicationReadyListener() {
        return new ApplicationReadyListener();
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("1024000MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400000MB");
        return factory.createMultipartConfig();
    }
    @Bean
    public EventBus messageBus(ContextWrappedExecutorService asyncEventExecutorService) {
        Feature.AsynchronousHandlerInvocation asynchronousHandlerInvocation = new Feature.AsynchronousHandlerInvocation();
        asynchronousHandlerInvocation.setExecutor(asyncEventExecutorService);
        EventBus bus = new EventBus(new BusConfiguration()
                .addFeature(Feature.SyncPubSub.Default())
                .addFeature(asynchronousHandlerInvocation)
                .addFeature(Feature.AsynchronousMessageDispatch.Default())
                .addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
                .setProperty(IBusConfiguration.Properties.BusId, "global bus"));
        return bus;
    }

    @Bean
    public ContextWrappedExecutorService asyncEventExecutorService() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setThreadNamePrefix("async-event-");
        threadPoolTaskExecutor.initialize();
        ContextWrappedExecutorService contextWrappedExecutorService = new ContextWrappedExecutorService(threadPoolTaskExecutor.getThreadPoolExecutor());
        return contextWrappedExecutorService;
    }

    @Bean
    public StringToUDCConverter stringToUDCConverter() {
        return new StringToUDCConverter();
    }
    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }
    @Bean
    public MeataApplicationReadyListener meataApplicationReadyListener(){
        return new MeataApplicationReadyListener();
    }

    @PostConstruct
    public void init() {
        ParserConfig.getGlobalInstance().getDeserializers().put(UDC.class, new UDCDeserializer());
    }
}
