package com.example.multimedia.controller;

import com.example.multimedia.service.SearchService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CookiesEason
 * 2018/08/18 12:24
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/videos")
    public ResultVo searchVideo(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "create_date") String sort,
                                 @RequestParam String searchContent){
        return searchService.searchVideo(page,order,sort,searchContent,true);
    }


    @RequestMapping("/articles")
    public ResultVo searchArticles(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(defaultValue = "create_date") String sort,
                                @RequestParam String searchContent){
        return searchService.searchArticle(page,order,sort,searchContent,true);
    }


}
