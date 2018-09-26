package com.example.multimedia.controller;

import com.example.multimedia.domian.Question;
import com.example.multimedia.domian.User;
import com.example.multimedia.dto.AdminUserDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.service.*;
import com.example.multimedia.service.QuestionService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
    private TagsService tagsService;

    @Autowired
    private SmallTagsService smallTagsService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AdminNoticeService adminNoticeService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private PictureService pictureService;

    @PostMapping("picture")
    public ResultVo uploadPicture(@RequestParam String imgUrl,@RequestParam String type){
            return pictureService.save(imgUrl, type);
    }

    @PostMapping("picture/update")
    public ResultVo updatePicture(@RequestParam Long id,
            @RequestParam String imgUrl,@RequestParam String type){
        return pictureService.update(id,imgUrl, type);
    }

    @DeleteMapping("picture/{id}")
    public ResultVo deletePicture(@PathVariable Long id){
        return pictureService.delete(id);
    }

    @GetMapping("users")
    private PageDTO<AdminUserDTO> findUsers(@RequestParam(defaultValue = "1") int page){
        return userService.findUsers(page);
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
    private PageDTO<AdminUserDTO> findUser(@RequestParam String searchContent){
        return userService.findByUsernameOrUserInfoNickname(searchContent);
    }

    @GetMapping("users/roles")
    private ResultVo getRoles(){
        return userService.getRoles();
    }

    @PostMapping("users/addAdmin")
    private ResultVo addAdmin(@RequestBody @Validated User user){
        // TODO: 2018/08/11 暂时未定 
        return userService.save(user,"ROLE_SENIOR_USER",true);
    }

    @PostMapping("users/changeRole")
    private ResultVo changeRole(@RequestParam Long userId,@RequestParam String role){
        return userService.changeRole(userId,role);
    }

    @GetMapping("tags")
    private ResultVo getTags(){
        return ResultVoUtil.success(tagsService.getTags());
    }

    @PostMapping("updateTag")
    private ResultVo updateTag(@RequestParam String oldTag,@RequestParam String tag){
        return tagsService.updateTag(oldTag, tag);
    }

    @PostMapping("addTag")
    private ResultVo addTag(@RequestParam String tag){
        return tagsService.addTag(tag);
    }

    @PostMapping("deleteTag")
    private ResultVo deleteTag(@RequestParam String tag){
        return tagsService.deleteTag(tag);
    }

    @GetMapping("smallTags")
    private ResultVo getSmallTag(@RequestParam(defaultValue = "0")int page){
        return ResultVoUtil.success(smallTagsService.findAll(page));
    }

    @PostMapping("addSmallTags")
    private ResultVo addSmallTag(@RequestParam String tag,@RequestParam String smallTag){
        return smallTagsService.save(smallTag,tag);
    }

    @PostMapping("updateSmallTags/{smallTagId}")
    private ResultVo updateSmallTag(@PathVariable Long smallTagId,String newTag){
        return smallTagsService.update(smallTagId,newTag);
    }

    @DeleteMapping("smallTags/{smallTagId}")
    private ResultVo deteleSmallTag(@PathVariable Long smallTagId){
        return smallTagsService.delete(smallTagId);
    }

    @GetMapping("videos")
    private ResultVo getVideos(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "asc") String order,
                               @RequestParam(defaultValue = "createDate") String sort,
                               @RequestParam(defaultValue = "true") Boolean enable,
                               @RequestParam(defaultValue = "true") Boolean auditing){
        return ResultVoUtil.success(videoService.findVideos(page,size,order,sort,enable,auditing));
    }

    @GetMapping("videos/search")
    private ResultVo searchVideo(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "create_date") String sort,
                                 @RequestParam Boolean enable,
                                 @RequestParam Boolean auditing,
                                 @RequestParam String searchContent){
        return ResultVoUtil.success(searchService.searchVideo(page,order,sort,0,-1,null,
                searchContent,enable,auditing));
    }

    @PostMapping("videos/updateVideo/{videoId}")
    private ResultVo updateVideo(@PathVariable Long videoId,
                                 @RequestParam String title,
                                 @RequestParam String introduction,
                                 @RequestParam(value = "smallTags") Set<String> smallTags,
                                 @RequestParam String tag,
                                 @RequestParam String imgUrl){
        return videoService.updateVideo(videoId,title,introduction,tag,smallTags,imgUrl);
    }

    @PostMapping("videos/enable/{videoId}")
    private ResultVo enableVideo(@PathVariable Long videoId,@RequestParam(defaultValue = "true") Boolean enable,
                                 @RequestParam(required = false) String reason){
        return videoService.enableVideo(videoId,enable,reason);
    }

   @DeleteMapping("videos/{videoId}")
    private ResultVo deleteVideo(@PathVariable Long videoId){
       return videoService.deleteByAdmin(videoId);
   }

    @GetMapping("articles")
    public ResultVo getArticles(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(defaultValue = "createDate") String sort,
                                @RequestParam(defaultValue = "true") Boolean enable){
        return ResultVoUtil.success(articleService.findAll(page,size,order,sort,enable));
    }

    @GetMapping("/articles/search")
    public ResultVo searchArticle(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "asc") String order,
                                  @RequestParam(defaultValue = "create_date") String sort,
                                  @RequestParam String searchContent){
        return ResultVoUtil.success(searchService.searchArticle(page, order, sort, searchContent,null,true));
    }

    @PostMapping("articles/update/{articleId}")
    public ResultVo updateArticle(@PathVariable Long articleId,
                                  @RequestParam String title,
                                  @RequestParam String text,
                                  @RequestParam String tag,
                                  @RequestParam(required = false) MultipartFile file,
                                  @RequestParam(value = "smallTags") Set<String> smallTags){
        return articleService.update(articleId, title, text, file,tag,smallTags);
    }

    @PostMapping("articles/enable/{articleId}")
    public ResultVo enableArticles(@PathVariable Long articleId,
                                   @RequestParam(required = false) String reason){
        return articleService.enable(articleId,reason);
    }

    @DeleteMapping("articles/{articleId}")
    public ResultVo deleteArticle(@PathVariable Long articleId){
        return articleService.deleteByAdmin(articleId);
    }

    @GetMapping("comments")
    private ResultVo getComments(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "createDate") String sort){
        return commentService.findAll(page,size,order,sort);
    }

    @GetMapping("comments/search")
    private ResultVo searchComment(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "asc") String order,
                                   @RequestParam(defaultValue = "createdate") String sort,
                                   @RequestParam String searchContent){
        return searchService.searchComment(page,order,sort,searchContent);
    }

    @DeleteMapping("comments/{commentId}")
    private ResultVo deleteComment(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return ResultVoUtil.success();
    }

    @GetMapping("replies")
    private ResultVo getreplies(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "asc") String order,
                                @RequestParam(defaultValue = "createDate") String sort){
        return replyService.findAll(page,size,order,sort);
    }

    @GetMapping("replies/search")
    private ResultVo searchReply(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "asc") String order,
                                 @RequestParam(defaultValue = "createdate") String sort,
                                 @RequestParam String searchContent){
        return searchService.searchReply(page, order, sort, searchContent);
    }

    @DeleteMapping("replies/{replyId}")
    private ResultVo deleteReply(@PathVariable Long replyId){
        replyService.deleteById(replyId);
        return ResultVoUtil.success();
    }


    @PostMapping("question")
    private ResultVo addQuestion(@RequestBody List<Question> question){
        return questionService.save(question);
    }

    @PostMapping("question/update")
    private ResultVo updateQuestion(@RequestBody Question question){
        return questionService.update(question);
    }

    @DeleteMapping("question/{questionId}")
    private ResultVo deleteQuestion(@PathVariable Long questionId){
        return questionService.delete(questionId);
    }

    @GetMapping("messages")
    private ResultVo getMessages(@RequestParam(defaultValue = "0") int page){
        return adminNoticeService.getAdminNotice(page);
    }

    @GetMapping("messages/count")
    private ResultVo countMessages(){
        return adminNoticeService.unRead();
    }

    @DeleteMapping("messages/{messageId}")
    private void deleteMessage(@PathVariable Long messageId){
        adminNoticeService.deleteById(messageId);
    }


    @PostMapping("/announcements")
    public ResultVo saveAnnouncements(@RequestParam String title,
                                      @RequestParam String text,@RequestParam String tag){
        return announcementService.save(title, text, tag);
    }

    @PostMapping("/announcements/update")
    public ResultVo updateAnnouncements(@RequestParam Long id,@RequestParam String title,
                                        @RequestParam String text,@RequestParam String tag){
        return announcementService.update(id,title, text, tag);
    }

    @DeleteMapping("/announcements/{id}")
    public ResultVo deleteAnnouncements(@PathVariable Long id){
        return announcementService.delete(id);
    }

}
