package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

/**
 * 视频
 * @author CookiesEason
 * 2018/08/03 16:38
 */
public interface VideoService {

    /**
     * 上传视频
     * @param title;
     * @param introduction;
     * @param tag;
     * @param multipartFile;
     * @return ResultVo
     */
    ResultVo uploadVideo(String title,String introduction,String tag,MultipartFile multipartFile);

    /**
     * 查找自己所有视频(已通过，和未通过)
     * @return ResultVo
     */
    ResultVo findMyVideos(int page,String order,boolean isEnable);

    /**
     * 删除视频
     * @param id;
     * @return ResultVo
     */
    ResultVo deleteById(long id);

    /**
     * 更新视频
     * @param title;
     * @param introduction;
     * @param tag;
     * @return ResultVo
     */
    ResultVo updateVideo(long id,String title, String introduction, String tag);

    /**
     * 获取单个视频信息
     * @param id
     * @return
     */
    ResultVo findById(long id);

}
