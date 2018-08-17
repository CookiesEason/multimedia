package com.example.multimedia.config;

import com.example.multimedia.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author CookiesEason
 * 2018/08/17 16:16
 */
@Component
public class ScheduleConfig {

    @Autowired
    private VideoService videoService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void timer(){
        videoService.deleteHistory();
    }


}
