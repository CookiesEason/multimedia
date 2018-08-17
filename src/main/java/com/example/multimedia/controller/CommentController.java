package com.example.multimedia.controller;

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
    @Qualifier(value = "VideoCommentService")
    private CommentService videoCommentService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService videoReplyService;

    @Autowired
    @Qualifier(value = "VideoCommentLikeService")
    private LikeService videoCommentLikeService;

    @Autowired
    @Qualifier(value = "VideoReplyLikeService")
    private LikeService videoReplyLikeService;

    @GetMapping("/access")
    private ResultVo checkAccessComment(){
        return userService.checkAccessComment();
    }

    @GetMapping("/video/{videoId}")
    private ResultVo getComments(@PathVariable Long videoId,@RequestParam(defaultValue = "0") int page){
        return videoCommentService.getComments(videoId,page);
    }

    @PostMapping("/video/add")
    private ResultVo createComment(@RequestParam Long videoId,@RequestParam String content){
        return videoCommentService.createComment(videoId,content);
    }

    @PostMapping("/video/reply")
    private ResultVo replyComment(@RequestParam Long commentId,@RequestParam String content,
                                  @RequestParam Long toUid){
        return videoReplyService.reply(commentId,content,toUid);
    }

    @DeleteMapping("/video/{commendId}")
    private ResultVo deleteComment(@PathVariable Long commendId){
        videoCommentService.deleteById(commendId);
        return ResultVoUtil.success();
    }

    @DeleteMapping("/video/reply/{replyId}")
    private ResultVo deleteReply(@PathVariable Long replyId){
        videoReplyService.deleteById(replyId);
        return ResultVoUtil.success();
    }

    @PostMapping("/video/replyLike/{replyId}")
    private void videoReplyLike(@PathVariable Long replyId){
        videoReplyLikeService.like(replyId);
    }

    @PostMapping("/video/commentLike/{commentId}")
    private void videoCommentLike(@PathVariable Long commentId){
        videoCommentLikeService.like(commentId);
    }

}
