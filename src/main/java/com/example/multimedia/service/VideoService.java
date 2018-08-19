package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

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
     * 展示所有视频(不分类)
     * @param page 页数
     * @param size 每页显示数量
     * @param order 排序字段
     * @param enable 审核通过与否
     * @return ResultVo
     */
    ResultVo findVideos(int page, int size,String order,String sort,Boolean enable);

    /**
     * 分类查找视频
     * @param page 页数
     * @param size 每页显示数量
     * @param order 排序字段
     * @param tag 分类
     * @return ResultVo
     */
    ResultVo findAllByTag(int page, int size,String order,String tag);

    /**
     * 查看某用户的所有视频
     * @param page 页数
     * @param size 每页显示数量
     * @param order 排序字段
     * @param userId 用户id
     * @return
     */
    ResultVo findAllByUserId(int page, int size,String order,Long userId);

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

    /**
     * 查找某视频
     * @param id
     * @return
     */
    Video findById(Long id);

    /**
     * 保存
     * @param video
     * @return
     */
    Video save(Video video);

    /**
     * 播放次数
     * @param videoId
     */
    void play(Long videoId);

    /**
     * 审核视频
     * @param videoId
     * @return
     */
    ResultVo enableVideo(Long videoId);

    /**
     * 记录观看历史
     * @param videoId
     */
    void saveHistory(Long videoId);

    /**
     * 获取观看历史
     * @return
     */
    ResultVo getHistory(int page);

    /**
     * 删除3天前的历史记录
     */
    void  deleteHistory();
}
