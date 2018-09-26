package com.example.multimedia.controller;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.example.multimedia.service.ArticleService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.ProblemService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 17:08
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    @Qualifier(value = "LikeService")
    private LikeService likeService;

    @GetMapping
    public ResultVo getArticles(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "desc") String order,
                                @RequestParam(defaultValue = "createDate") String sort){
        return ResultVoUtil.success(articleService.findAll(page,size,order,sort,true));
    }

    @GetMapping("/tag")
    public ResultVo getArticlesByTag(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "desc") String order,
                                     @RequestParam(defaultValue = "createDate") String sort,
                                     @RequestParam String tag){
        return ResultVoUtil.success(articleService.findAllByTag(page,size,order,sort,tag));
    }

    @GetMapping("/smallTag")
    public ResultVo getArticlesBySmallTag(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam String smallTag,
                                          @RequestParam(defaultValue = "createDate") String sort){
        return ResultVoUtil.success(articleService.findAllBySmallTag(page, size, smallTag, sort));
    }

    @GetMapping("/{articleId}")
    public ResultVo getArticle(@PathVariable Long articleId,@RequestParam(defaultValue = "true") Boolean enable){
        return articleService.findById(articleId,enable);
    }

    @GetMapping("/user/{userId}")
    public ResultVo getUserArticle(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "desc") String order,
                                   @RequestParam(defaultValue = "createDate") String sort){
        return ResultVoUtil.success(articleService.findUserAll(userId,page,size,order,sort));
    }

    @GetMapping("/me")
    public ResultVo getMyArticle(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "desc") String order,
                                 @RequestParam(defaultValue = "createDate") String sort,
                                 @RequestParam(defaultValue = "true") Boolean enable){
        return ResultVoUtil.success(articleService.findMyAll(page, size, order, sort,enable));
    }

    @GetMapping("/userLike/{userId}")
    public ResultVo getMyLikeVideos(@PathVariable Long userId,@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10")int size){
        return ResultVoUtil.success(articleService.findAllByLike(userId,page,size));
    }

    @PostMapping
    public ResultVo saveArticle(@RequestParam String title, @RequestParam String text,
                                @RequestParam String tag, @RequestParam(value = "file") MultipartFile file,
                                @RequestParam(value = "smallTags")Set<String> smallTags) {
        return articleService.save(title, text, tag,file,smallTags);
    }

    @PostMapping("/update/{articleId}")
    public ResultVo updateArticle(@PathVariable Long articleId,@RequestParam String title,
                                   @RequestParam String text,@RequestParam String tag,
                                  @RequestParam(required = false) MultipartFile file,
                                  @RequestParam(value = "smallTags")Set<String> smallTags){
        return articleService.update(articleId, title, text, file, tag,smallTags);
    }

    @DeleteMapping("/{articleId}")
    public ResultVo deleteArticle(@PathVariable Long articleId){
        return articleService.deleteById(articleId);
    }

    @PostMapping("/like/{articleId}")
    public Long likeArticle(@PathVariable Long articleId){
        return likeService.like(articleId, Topic.ARTICLE);
    }

    @PostMapping("/report/{articleId}")
    public ResultVo report(@PathVariable Long articleId,
                           @RequestParam String reason,
                           @RequestParam String reasonContent){
        return articleService.reportArticle(articleId,reason,reasonContent);
    }

    @GetMapping("/proportion/{userId}")
    public ResultVo proportion(@PathVariable Long userId){
        return articleService.countWorksProportion(userId);
    }

    @GetMapping("/problems/{articleId}")
    public ResultVo problems(@PathVariable Long articleId){
        return articleService.problems(articleId);
    }

}
