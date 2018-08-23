package com.example.multimedia.controller;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.ReplyService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/05 14:05
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    @Qualifier(value = "CommentLikeService")
    private LikeService commentLikeService;

    @Autowired
    @Qualifier(value = "ReplyLikeService")
    private LikeService replyLikeService;

    @GetMapping("/access")
    private ResultVo checkAccessComment(){
        return userService.checkAccessComment();
    }

    @GetMapping("/video/{videoId}")
    private ResultVo getVideosComments(@PathVariable Long videoId,
                                       @RequestParam(defaultValue = "likeCount") String sort,
                                       @RequestParam(defaultValue = "0") int page){
        return commentService.getComments(videoId,Topic.VIDEO,page,sort);
    }

    @GetMapping("/article/{articleId}")
    private ResultVo getArticleComments(@PathVariable Long articleId,
                                        @RequestParam(defaultValue = "likeCount") String sort,
                                        @RequestParam(defaultValue = "0") int page){
        return commentService.getComments(articleId,Topic.ARTICLE,page,sort);
    }

    @GetMapping("/video/me/{videoId}")
    private ResultVo getMyVideoComments(@PathVariable Long videoId){
        return commentService.findMyself(videoId,Topic.VIDEO);
    }

    @GetMapping("/article/me/{articleId}")
    private ResultVo getMyArticleComments(@PathVariable Long videoId){
        return commentService.findMyself(videoId,Topic.ARTICLE);
    }

    @PostMapping("/video/add")
    private ResultVo createComment(@RequestParam Long videoId,@RequestParam String content){
        return commentService.createComment(videoId,content, Topic.VIDEO);
    }

    @PostMapping("/article/add")
    private ResultVo createArticleComment(@RequestParam Long articleId,@RequestParam String content){
        return commentService.createComment(articleId,content, Topic.ARTICLE);
    }

    @PostMapping("/reply")
    private ResultVo replyComment(@RequestParam Long commentId,@RequestParam String content,
                                  @RequestParam Long toUid){
        return replyService.reply(commentId,content,toUid);
    }

    @DeleteMapping("/{commendId}")
    private ResultVo deleteComment(@PathVariable Long commendId){
        commentService.deleteById(commendId);
        return ResultVoUtil.success();
    }

    @DeleteMapping("/reply/{replyId}")
    private ResultVo deleteReply(@PathVariable Long replyId){
        replyService.deleteById(replyId);
        return ResultVoUtil.success();
    }

    @PostMapping("/replyLike/{replyId}")
    private void replyLike(@PathVariable Long replyId){
        replyLikeService.like(replyId,null);
    }

    @PostMapping("/commentLike/{commentId}")
    private void commentLike(@PathVariable Long commentId){
        commentLikeService.like(commentId,null);
    }

}
