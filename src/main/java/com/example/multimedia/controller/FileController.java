package com.example.multimedia.controller;

import com.example.multimedia.service.FileService;
import com.example.multimedia.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CookiesEason
 * 2018/07/25 15:20
 */
@RestController
@RequestMapping("/file/api/")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResultVo upload(@RequestParam(value = "file")MultipartFile multipartFile){
        return fileService.uploadFile(multipartFile);
    }

}
