package com.my.biz.common.entity;


import com.my.biz.common.udc.UDC;
import com.my.biz.common.udc.UDCUserType;
import com.my.biz.common.util.SpringContextHolder;
import com.my.common.copy.Copier;
import com.my.common.exception.CPBusinessException;
import com.my.common.utils.BeanCopier;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 96230 on 2017/5/29.
 */
@MappedSuperclass
@TypeDefs({@TypeDef(defaultForType = UDC.class, name = "udcType", typeClass = UDCUserType.class),})
public class BaseEntity implements Serializable {
    public static final Long NULL_MERCHANT_ID = 1L;
    private static boolean inited = false;
    private static volatile AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = null;

    /**
     * 转换领域对象为目标类型
     *
     * @param clazz 目标类型
     */
    public <T> T to(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Copier.copyBean(this, t, Boolean.FALSE, null);
            return t;
        } catch (Exception e) {
            CPBusinessException.throwIt("实体与dto转换失败");
            return null;
        }
    }

    /**
     * 从DTO拷贝属性到领域对象
     * <p>
     * 拷贝策略为：1.忽略DTO中的null 2. 忽略DTO属性不兼容 3.忽略DTO中的某些属性
     * </p>
     *
     * @param dto              传输对象
     * @param ingoreProperties 忽略拷贝的属性,一般为DTO中的非基本类型的属性,举例:UserDto中包含了DepartmentDto,DepartmentDto 拷贝时需要忽略
     */
    public void from(Object dto, String... ingoreProperties) {
        BeanCopier.copy(dto, this, BeanCopier.CopyStrategy.IGNORE_NULL, BeanCopier.NoMatchingRule.IGNORE, ingoreProperties);
    }

    public void fromContainNUll(Object dto) {
        BeanCopier.copy(dto, this, BeanCopier.CopyStrategy.CONTAIN_NULL, BeanCopier.NoMatchingRule.EXCEPTION);
    }

    public void fromContainNUll(Object dto, String... ingoreProperties) {
        BeanCopier.copy(dto, this, BeanCopier.CopyStrategy.CONTAIN_NULL, BeanCopier.NoMatchingRule.EXCEPTION, ingoreProperties);
    }

    /**
     * 把实体对象list转换为目标对象List
     */
    public static <T, S extends BaseEntity> List<T> map(List<S> list, Class<T> clazz) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<T>();
        }
        List<T> ts = new ArrayList<>(list.size());
        for (S s : list) {
            ts.add(s.to(clazz));
        }
        return ts;
    }

    public static void inited() {
        inited = true;
    }

    public BaseEntity() {
        if (inited) {
            autowire();
        }
    }

    /**
     * 注入Entity中的@Autowired
     */
    private void autowire() {
        if (autowiredAnnotationBeanPostProcessor == null) {
            synchronized (AggEntity.class) {
                if (autowiredAnnotationBeanPostProcessor == null) {
                    List<BeanPostProcessor>
                            beanPostProcessors = ((AbstractBeanFactory) (((AbstractApplicationContext) SpringContextHolder.getApplicationContext()).getBeanFactory())).getBeanPostProcessors();
                    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                        if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor && beanPostProcessor.getClass().getName().contains("AutowiredAnnotationBeanPostProcessor")) {
                            autowiredAnnotationBeanPostProcessor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                        }
                    }
                }
            }
        }
        autowiredAnnotationBeanPostProcessor.postProcessPropertyValues(null, null, this, getClass().getName());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'")
    protected Long id;

    @Column(name = "raw_add_time", insertable = false, updatable = false,
            columnDefinition = " timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    protected Date rawAddTime = null;

    @Column(name = "raw_update_time", insertable = false, updatable = false,
            columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
    protected Date rawUpdateTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }
}
