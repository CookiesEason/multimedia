package com.example.multimedia.controller;

import com.example.multimedia.service.ArticleService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CookiesEason
 * 2018/08/28 16:37
 */
@RestController
@RequestMapping("/api/data/")
public class HomeDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @GetMapping("/newCount")
    public int newWorks(@RequestParam(defaultValue = "3") int day){
        return articleService.countArticlesForDays(day)+videoService.countVideosForDays(day);
    }

    @GetMapping("/webClick")
    public int webClick(){
        // TODO: 2018/08/28  网站浏览 redis 自增
        return 0;
    }

    @GetMapping("/newRegisterCount")
    public int newRegisterCount(@RequestParam(defaultValue = "30") int day){
        return userService.newRegisterCountForDays(day);
    }

}
