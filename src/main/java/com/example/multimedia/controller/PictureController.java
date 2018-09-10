package com.example.multimedia.controller;

import com.example.multimedia.service.PictureService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CookiesEason
 * 2018/09/09 16:00
 */
@RequestMapping("/api/pictures")
@RestController
public class PictureController {


    @Autowired
    private PictureService pictureService;

    @GetMapping
    public ResultVo getPictures(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size){
        return pictureService.findAll(page,size);
    }

    @GetMapping("/type")
    public ResultVo getPicturesByTag(@RequestParam String type,
                                     @RequestParam(defaultValue = "0") int page, @RequestParam int size){
        return pictureService.findByType(type, page, size);
    }

}
