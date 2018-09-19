package com.example.multimedia.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author CookiesEason
 * 2018/08/12 13:42
 */
@Slf4j
public class ElasticSearchConfiguration implements InitializingBean {

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("*****************es_config*************************");
        log.info("es.set.netty.runtime.available.processors:{}", System.getProperty("es.set.netty.runtime.available.processors"));
        log.info("***************************************************");
    }
}

