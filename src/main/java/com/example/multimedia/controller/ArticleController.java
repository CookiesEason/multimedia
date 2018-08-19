package com.example.multimedia.controller;

import com.example.multimedia.service.ArticleService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/18 17:08
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResultVo getArticles(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "desc") String order,
                                @RequestParam(defaultValue = "createDate") String sort){
        return articleService.findAllByEnable(page,size,order,sort,true);
    }

    @GetMapping("/tag")
    public ResultVo getArticlesByTag(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "desc") String order,
                                     @RequestParam(defaultValue = "createDate") String sort,
                                     @RequestParam String tag){
        return articleService.findAllByTag(page,size,order,sort,tag,true);
    }

    @GetMapping("/{articleId}")
    public ResultVo getArticle(@PathVariable Long articleId){
        return articleService.findById(articleId);
    }

    @GetMapping("/user/{userId}")
    public ResultVo getUserArticle(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "desc") String order,
                                   @RequestParam(defaultValue = "createDate") String sort){
        return articleService.findUserAll(userId,page,size,order,sort);
    }

    @GetMapping("/me")
    public ResultVo getMyArticle(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "desc") String order,
                                 @RequestParam(defaultValue = "createDate") String sort,
                                 @RequestParam(defaultValue = "true")Boolean enable){
        return articleService.findMyAll(page, size, order, sort, enable);
    }

    @PostMapping
    public ResultVo saveArticle(@RequestParam String title,@RequestParam String text,
                                 @RequestParam String tag) {
        return articleService.save(title, text, tag);
    }

    @PostMapping("/update/{articleId}")
    public ResultVo updateArticle(@PathVariable Long articleId,@RequestParam String title,
                                   @RequestParam String text,@RequestParam String tag){
        return articleService.update(articleId, title, text, tag);
    }

    @DeleteMapping("/{articleId}")
    public ResultVo deleteArticle(@PathVariable Long articleId){
        return articleService.delete(articleId);
    }

}
