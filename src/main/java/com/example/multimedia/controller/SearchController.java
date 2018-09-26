package com.example.multimedia.controller;

import com.example.multimedia.service.SearchService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/users")
    private ResultVo findUser(@RequestParam String searchContent){
        return searchService.searchUser(searchContent);
    }


    @RequestMapping("/videos")
    public ResultVo searchVideo(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "desc") String order,
                                 @RequestParam(defaultValue = "create_date") String sort,
                                @RequestParam(defaultValue = "0") int startTime,
                                @RequestParam(defaultValue = "999999") int endTime,
                                 @RequestParam(required = false) String tag,
                                 @RequestParam String searchContent){
        return ResultVoUtil.success(searchService.searchVideo(page,order,sort,startTime,endTime,tag,
                searchContent,true,true));
    }


    @RequestMapping("/articles")
    public ResultVo searchArticles(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "desc") String order,
                                @RequestParam(defaultValue = "create_date") String sort,
                                @RequestParam(required = false) String tag,
                                @RequestParam String searchContent){
        return ResultVoUtil.success(searchService.searchArticle(page,order,sort,searchContent,tag,true));
    }


}
