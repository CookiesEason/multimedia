package com.example.multimedia.controller;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.videodomian.search.VideoSearch;
import com.example.multimedia.repository.search.VideoSearchRepository;
import com.example.multimedia.service.*;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/02 19:41
 */
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    @Qualifier(value = "VideoCommentService")
    private CommentService videoCommentService;

    @Autowired
    @Qualifier(value = "VideoReplyService")
    private ReplyService videoReplyService;

    @Autowired
    private VideoSearchService videoSearchService;

    @GetMapping("users")
    private ResultVo findUsers(@RequestParam(defaultValue = "0") int page){
        String role = "%USER%";
        return userService.findUsers(page,role);
    }

    @PostMapping("users/{userId}")
    private ResultVo updateUserById(@PathVariable Long userId){
        // TODO: 2018/08/10 暂时不写.
        return ResultVoUtil.success();
    }

    @PostMapping("users/enable/{userId}")
    private ResultVo enableUserByUserId(@PathVariable Long userId){
        return userService.enableUserByUserId(userId);
    }

    @DeleteMapping("users/{userId}")
    private ResultVo deleteUserById(@PathVariable Long userId){
        return userService.deleteByUserId(userId);
    }

    @GetMapping("users/search")
    private ResultVo findUser(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String nickname,
                              @RequestParam(required = false) String email){
        return userService.findByUsernameOrEmailOrUserInfoNickname(username,email,nickname);
    }

    @GetMapping("users/admins")
    private ResultVo getAdmins(@RequestParam(defaultValue = "0") int page){
        String role = "%ADMIN%";
        return userService.findUsers(page,role);
    }

    @GetMapping("users/roles")
    private ResultVo getRoles(){
        return userService.getRoles();
    }

    @PostMapping("users/addAdmin")
    private ResultVo addAdmin(@RequestBody @Validated User user){
        // TODO: 2018/08/11 暂时未定 
        return userService.save(user,user.getRoleList().get(1).getRole());
    }

    @PostMapping("users/changeRole")
    private ResultVo changeRole(@RequestParam Long userId,@RequestParam String role){
        return userService.changeRole(userId,role);
    }

    @GetMapping("videos/tags")
    private ResultVo getTags(){
        return videoService.getTags();
    }

    @PostMapping("videos/updateTag")
    private ResultVo updateTag(@RequestParam String oldTag,@RequestParam String tag){
        return videoService.updateTag(oldTag, tag);
    }

    @PostMapping("videos/addTag")
    private ResultVo addTags(@RequestParam String tag){
        return videoService.addTag(tag);
    }

    @DeleteMapping("videos/tags/{tag}")
    private ResultVo deleteTag(@PathVariable String tag){
        return videoService.deleteTag(tag);
    }

    @GetMapping("videos")
    private ResultVo getVideos(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "asc") String order,
                               @RequestParam(defaultValue = "createDate") String sort,
                               @RequestParam Boolean enable){
        return videoService.findVideos(page,size,order,sort,enable);
    }

    @GetMapping("videos/search")
    private ResultVo searchVideo(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "create_date") String sort,
                                 @RequestParam String searchContent){
        return videoSearchService.searchVideo(page,order,sort,searchContent);
    }

    @PostMapping("videos/updateVideo/{videoId}")
    private ResultVo updateVideo(@PathVariable Long videoId,
                                 @RequestParam String title,
                                 @RequestParam String introduction,
                                 @RequestParam String tag){
        return videoService.updateVideo(videoId,title,introduction,tag);
    }

    @PostMapping("videos/enable/{videoId}")
    private ResultVo enableVideo(@PathVariable Long videoId){
        return videoService.enableVideo(videoId);
    }

   @DeleteMapping("videos/{videoId}")
    private ResultVo deleteVideo(@PathVariable Long videoId){
       return videoService.deleteById(videoId);
   }

   @GetMapping("videos/comments")
    private ResultVo getComments(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "createDate") String sort){
        return videoCommentService.findAll(page,size,sort);
   }

   @GetMapping("videos/comments/search")
   private ResultVo searchComment(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "asc") String order,
                                  @RequestParam(defaultValue = "create_date") String sort,
                                  @RequestParam String searchContent){
        return videoSearchService.searchVideoComment(page,order,sort,searchContent);
   }

   @DeleteMapping("videos/comments/{commentId}")
    private ResultVo deleteComment(@PathVariable Long commentId){
       videoCommentService.deleteById(commentId);
       return ResultVoUtil.success();
   }

    @GetMapping("videos/replies")
    private ResultVo getreplies(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "createDate") String sort){
        return videoReplyService.findAll(page,size,sort);
    }

    @GetMapping("videos/replies/search")
    private ResultVo searchReply(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "create_date") String sort,
                                 @RequestParam String searchContent){
        return videoSearchService.searchVideoReply(page, order, sort, searchContent);
    }

    @DeleteMapping("videos/replies/{replyId}")
    private ResultVo deleteReply(@PathVariable Long replyId){
        videoReplyService.deleteById(replyId);
        return ResultVoUtil.success();
    }


}
