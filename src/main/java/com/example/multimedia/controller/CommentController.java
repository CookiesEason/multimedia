package com.example.multimedia.controller;

import com.example.multimedia.service.CommentService;
import com.example.multimedia.service.ReplyService;
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
    @Qualifier(value = "VideoCommentService")
    private CommentService commentService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService replyService;

    @GetMapping("/video/{videoId}")
    private ResultVo getComments(@PathVariable Long videoId,@RequestParam(defaultValue = "0") int page){
        return commentService.getComments(videoId,page);
    }

    @PostMapping("/video/add")
    private ResultVo createComment(@RequestParam Long videoId,@RequestParam String content){
        return commentService.createComment(videoId,content);
    }

    @PostMapping("/video/reply")
    private ResultVo replyComment(@RequestParam Long commentId,@RequestParam String content,
                                  @RequestParam Long toUid){
        return replyService.reply(commentId,content,toUid);
    }

    @DeleteMapping("/video/{commendId}")
    private ResultVo deleteComment(@PathVariable Long commendId){
        commentService.deleteById(commendId);
        return ResultVoUtil.success();
    }

    @DeleteMapping("/video/reply/{replyId}")
    private ResultVo deleteReply(@PathVariable Long replyId){
        replyService.deleteById(replyId);
        return ResultVoUtil.success();
    }

}
