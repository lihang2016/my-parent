
package com.my.biz.common.udc.biz;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.my.biz.common.udc.domain.entity.UDCType;
import com.my.biz.common.udc.domain.repository.UDCTypeRepository;
import com.my.common.exception.SystemException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 */
@Component
public class UDCComponent implements UDCService, InitializingBean {

    Cache<String, UDCType> cache = CacheBuilder.newBuilder().maximumSize(1000)
            .build();

    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private UDCTypeRepository udcTypeRepository;

    public UDCType findByCode(String code) {
        try {
            return cache.get(code, () -> transactionTemplate.execute(status -> udcTypeRepository.findByCode(code)));
        } catch (Exception e) {
            throw new SystemException("UDCType code=" + code + "不存在", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setReadOnly(true);
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate = new TransactionTemplate(platformTransactionManager, defaultTransactionDefinition);
    }

}
