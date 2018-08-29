package com.example.multimedia.service.impl;

import com.example.multimedia.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author CookiesEason
 * 2018/08/28 18:04
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public long getIncr(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.add(Calendar.MONTH, +1);
        aCalendar.set(Calendar.DAY_OF_MONTH,1);
        counter.expireAt(aCalendar.getTime());
        return counter.incrementAndGet();
    }


}
