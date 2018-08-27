package com.example.multimedia.service;

import com.alibaba.fastjson.JSONObject;
import com.example.multimedia.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CookiesEason
 * 2018/07/25 14:20
 */
public interface FileService {

    /**
     * 上传文件
     * @param multipartFile
     * @return 文件地址
     */
    ResultVo uploadFile(MultipartFile multipartFile);

    /**
     * 编辑器上传
     * @param multipartFile
     * @return
     */
    JSONObject uploadWangFile(MultipartFile multipartFile);
}
