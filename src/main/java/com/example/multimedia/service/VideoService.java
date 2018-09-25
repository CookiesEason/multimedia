package com.example.multimedia.service;

import com.example.multimedia.domian.maindomian.Video;
import com.example.multimedia.dto.VideosDTO;
import com.example.multimedia.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

/**
 * 视频
 * @author CookiesEason
 * 2018/08/03 16:38
 */
public interface VideoService {

    String getUploadSignature() throws UnsupportedEncodingException;


    /**
     * 上传视频
     * @param title
     * @param introduction
     * @param tag
     * @param smallTags
     * @param imgUrl
     * @param videoUrl
     * @param fileId
     * @return
     */
    ResultVo uploadVideo(String title, String introduction, String tag, Set<String> smallTags,
                         String imgUrl,
                         String videoUrl,String fileId,Long time);

    /**
     * 查找自己所有视频(已通过，和未通过)
     * @return ResultVo
     */
    ResultVo findMyVideos(int page,int size,String order,boolean isEnable,boolean isAuditing);

    /**
     * 展示所有视频(不分类)
     * @param page 页数
     * @param size 每页显示数量
     * @param order 排序字段
     * @param enable 禁用
     * @param auditing 审核状态
     * @return ResultVo
     */
    VideosDTO findVideos(int page, int size, String order, String sort, Boolean enable, Boolean auditing);

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
     * 后台删除视频
     * @param id;
     * @return ResultVo
     */
    ResultVo deleteByAdmin(long id);

    /**
     * 更新视频
     * @param title;
     * @param introduction;
     * @param tag;
     * @return ResultVo
     */
    ResultVo updateVideo(long id,String title, String introduction, String tag, Set<String> smallTags,String imgUrl);

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
    ResultVo enableVideo(Long videoId,Boolean enable,String reason);

    /**
     * 记录观看历史
     * @param videoId
     */
    void saveHistory(Long videoId);

    /**
     * 获取观看历史
     * @return
     */
    ResultVo getHistory(int page, int size);

    /**
     * 删除3天前的历史记录
     */
    void  deleteHistory();

    /**
     * 举报
     * @param videoId
     * @param reason
     * @param content
     * @return
     */
    ResultVo reportVideo(Long videoId,String reason,String content);

    /**
     * 按某标签查找
     * @param page
     * @param smallTag
     * @return
     */
    ResultVo findAllBySmallTag(int page, int size,String smallTag,String sort);

    /**
     * 最新投稿 过去三天
     * @return
     */
    int countVideosForDays(int day);

    /**
     * 点赞过的视频
     * @return
     */
    ResultVo findAllByLike(Long userId,int page,int size);

    /**
     * 作品类别占比
     * @param userId
     * @return
     */
    ResultVo countWorksProportion(Long userId);

    /**
     * 作品分别计数
     * @return
     */
    ResultVo count();

    /**
     * 问题
     * @param id
     * @return
     */
    ResultVo problems(Long id);

    /**
     * 后台作品比较
     * @return
     */
    List<Object> worksCompare();
}
