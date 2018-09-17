package com.example.multimedia.controller;

import com.example.multimedia.service.SmallTagsService;
import com.example.multimedia.service.TagsService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CookiesEason
 * 2018/08/25 21:18
 */
@RestController
public class TagController {

    @Autowired
    private SmallTagsService smallTagsService;

    @Autowired
    private TagsService tagsService;

    @GetMapping("/api/tags")
    private ResultVo getTags(){
        return ResultVoUtil.success(tagsService.getTags());
    }

    @RequestMapping("/api/smallTags")
    private ResultVo getSmallTagsByTag(@RequestParam String tag){
        return ResultVoUtil.success(smallTagsService.getSmallTagByTag(tag));
    }

    @RequestMapping("/api/tags/hot")
    private ResultVo getHotWorksByTag(@RequestParam String tag){
        return tagsService.hotWorksByTag(tag);
    }

}
