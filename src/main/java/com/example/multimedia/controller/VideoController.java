package com.example.multimedia.controller;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.TagsService;
import com.example.multimedia.service.VideoService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/03 16:46
 */
@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    @Qualifier(value = "LikeService")
    private LikeService videoLikeService;


    @GetMapping("/watchHistory")
    private ResultVo watchHistory(@RequestParam(defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "10") int size){
        return videoService.getHistory(page,size);
    }


    @PostMapping("/signature")
    @ResponseBody
    public String signature() throws UnsupportedEncodingException {
        return videoService.getUploadSignature();
    }

    @PostMapping
    @ResponseBody
    private ResultVo uploadVideo(@RequestParam String title,@RequestParam String introduction,
                                 @RequestParam String tag,@RequestParam String imgUrl,
                                 @RequestParam(value = "smallTags") Set<String> smallTags,
                                 @RequestParam String videoUrl,@RequestParam String fileId,
                                 @RequestParam Long time){
        return videoService.uploadVideo(title,introduction,tag,smallTags,imgUrl,videoUrl,fileId,time);
    }

    @GetMapping("/{id}")
    @ResponseBody
    private ResultVo getVideo(@PathVariable long id){
        return videoService.findById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    private ResultVo updateVideo(@PathVariable long id,@RequestParam String title,
                                 @RequestParam String introduction,
                                 @RequestParam String tag,
                                 @RequestParam(value = "smallTags") Set<String> smallTags,
                                 @RequestParam String imgUrl){
        return videoService.updateVideo(id,title,introduction,tag,smallTags,imgUrl);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    private ResultVo deleteVideo(@PathVariable long id){
        return videoService.deleteById(id);
    }

    @GetMapping("/me")
    @ResponseBody
    private ResultVo findMyVideos(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "createDate") String sort,
                                  @RequestParam(defaultValue = "true") boolean enable,
                                  @RequestParam(defaultValue = "true") boolean isAuditing){
        // TODO: 2018/08/08  可能按需求 对 视频也要进行分类 
        return videoService.findMyVideos(page,size,sort,enable,isAuditing);
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    private ResultVo findUserVideos(@PathVariable Long userId,
                                    @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "createDate") String sort){
        return videoService.findAllByUserId(page,size,sort,userId);
    }

    @GetMapping("/count")
    public ResultVo count(){
        return videoService.count();
    }

    @GetMapping
    @ResponseBody
    private ResultVo findVideos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "desc") String order,
                                @RequestParam(defaultValue = "createDate") String sort){
       return ResultVoUtil.success(videoService.findVideos(page,size,order,sort,true,true));
    }

    @GetMapping("/tag")
    @ResponseBody
    private ResultVo findVideosByTag(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "createDate") String sort,
                                     @RequestParam String tag){
        return videoService.findAllByTag(page,size,sort,tag);
    }

    @GetMapping("/smallTag")
    public ResultVo getVideosBySmallTag(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam String smallTag,
                                          @RequestParam(defaultValue = "createDate") String sort){
        return videoService.findAllBySmallTag(page, size, smallTag, sort);
    }

    @GetMapping("/userLike/{userId}")
    public ResultVo getMyLikeVideos(@PathVariable Long userId,@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10")int size){
        return videoService.findAllByLike(userId,page,size);
    }

    @PostMapping("/like/{videoId}")
    private Long videoLike(@PathVariable Long videoId){
        return videoLikeService.like(videoId, Topic.VIDEO);
    }

    @PostMapping("/play/{videoId}")
    private void play(@PathVariable Long videoId){
        videoService.play(videoId);
    }

    @PostMapping("/report/{videoId}")
    public ResultVo report(@PathVariable Long videoId,
                           @RequestParam String reason,
                           @RequestParam String reasonContent){
        return videoService.reportVideo(videoId,reason,reasonContent);
    }

    @GetMapping("/proportion/{userId}")
    public ResultVo proportion(@PathVariable Long userId){
        return videoService.countWorksProportion(userId);
    }

    @GetMapping("/problems/{videoId}")
    public ResultVo problems(@PathVariable Long videoId){
        return videoService.problems(videoId);
    }

}
