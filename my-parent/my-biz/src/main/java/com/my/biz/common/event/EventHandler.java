package com.my.biz.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 */
public abstract class EventHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventBus bus;

    @PostConstruct
    private void init() {
        bus.subscribe(this);
    }

    @PreDestroy
    private void destroy() {
        bus.unsubscribe(this);
    }
}
