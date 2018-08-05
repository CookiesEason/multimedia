package com.example.multimedia.controller;

import com.example.multimedia.service.VideoService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/{id}")
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

    @GetMapping
    @ResponseBody
    private ResultVo findAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "createDate") String sort,
                             @RequestParam(defaultValue = "true") boolean isEnable){
        return videoService.findMyVideos(page,sort,isEnable);
    }


}
