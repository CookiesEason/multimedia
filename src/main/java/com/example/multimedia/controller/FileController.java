package com.example.multimedia.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.service.FileService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CookiesEason
 * 2018/08/27 9:53
 */
@RequestMapping("/api/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/editor/upload")
    public JSONObject uploadWangFile(@RequestParam MultipartFile multipartFile){
        return fileService.uploadWangFile(multipartFile);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile multipartFile){
        return (String) fileService.uploadFile(multipartFile).getData();
    }

}
