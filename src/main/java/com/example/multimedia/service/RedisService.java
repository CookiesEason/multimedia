package com.example.multimedia.service;

/**
 * @author CookiesEason
 * 2018/08/28 18:04
 */
public interface RedisService {


    /**
     * 自增
     * @param key
     * @return
     */
    long getIncr(String key);

}
