package com.example.multimedia.controller;

import com.example.multimedia.service.AnnouncementService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CookiesEason
 * 2018/08/28 11:01
 */
@RequestMapping("/api/announcements")
@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/{id}")
    public ResultVo getAnnounces(@PathVariable Long id){
        return announcementService.findOne(id);
    }

    @GetMapping
    public ResultVo getAnnounces(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        return announcementService.findAll(page, size);
    }

    @GetMapping("/tag")
    public ResultVo getAnnounces(@RequestParam String tag,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        return announcementService.findByTag(tag, page, size);
    }

}
