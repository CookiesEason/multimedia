package com.example.multimedia.controller;

import com.example.multimedia.service.LikeService;
import com.example.multimedia.service.VideoService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Qualifier(value = "VideoLikeService")
    private LikeService videoLikeService;

    @GetMapping("tags")
    private ResultVo getTags(){
        return videoService.getTags();
    }

    @GetMapping("/watchHistory")
    private ResultVo watchHistory(@RequestParam(defaultValue = "0")int page){
        return videoService.getHistory(page);
    }

    @PostMapping
    @ResponseBody
    private ResultVo uploadVideo(@RequestParam String title,@RequestParam String introduction,
                                 @RequestParam String tag, @RequestParam(value = "file",required = false) MultipartFile multipartFile){
        return videoService.uploadVideo(title,introduction,tag,multipartFile);
    }

    @GetMapping("/{id}")
    @ResponseBody
    private ResultVo getVideo(@PathVariable long id){
        return videoService.findById(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    private ResultVo updateVideo(@PathVariable long id,@RequestParam String title,
                                 @RequestParam String introduction, @RequestParam String tag){
        return videoService.updateVideo(id,title,introduction,tag);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    private ResultVo deleteVideo(@PathVariable long id){
        return videoService.deleteById(id);
    }

    @GetMapping("/user")
    @ResponseBody
    private ResultVo findMyVideos(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "createDate") String sort,
                                  @RequestParam(defaultValue = "true") boolean enable){
        // TODO: 2018/08/08  可能按需求 对 视频也要进行分类 
        return videoService.findMyVideos(page,sort,enable);
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    private ResultVo findUserVideos(@PathVariable Long userId,
                                    @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "createDate") String sort){
        return videoService.findAllByUserId(page,size,sort,userId);
    }

    @GetMapping
    @ResponseBody
    private ResultVo findVideos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "desc") String order,
                                @RequestParam(defaultValue = "createDate") String sort){
       return videoService.findVideos(page,size,order,sort,true);
    }

    @GetMapping("/tag")
    @ResponseBody
    private ResultVo findVideosByTag(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "createDate") String sort,
                                     @RequestParam String tag){
        return videoService.findAllByTag(page,size,sort,tag);
    }

    @PostMapping("/like/{videoId}")
    private void videoLike(@PathVariable Long videoId){
        videoLikeService.like(videoId);
    }

    @PostMapping("/play/{videoId}")
    private void play(@PathVariable Long videoId){
        videoService.play(videoId);
    }


}
