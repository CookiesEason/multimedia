package com.example.multimedia.service;

import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/12 15:41
 */
public interface VideoSearchService {

    /**
     *搜索视频
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideo(int page,String order,String sort,String searchContent);

    /**
     *搜索评论
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideoComment(int page,String order,String sort,String searchContent);

    /**
     * 搜索回复
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideoReply(int page,String order,String sort,String searchContent);

    /**
     * 删除视频
     * @param id
     */
    void  deleteVideoById(Long id);

    /**
     * 删除视频下的评论
     * @param id
     */
    void deleteAllByVideoId(Long id);

    /**
     * 删除视频下的回复
     * @param ids
     */
    void deleteReplyAllByComment_idIn(List<Long> ids);

    /**
     * 删除评论
     * @param id
     */
    void deleteCommentById(Long id);

    /**
     * 删除评论下的回复
     * @param id
     */
    void deleteReplyAllByCommentId(Long id);

    /**
     * 删除回复
     * @param id
     */
    void deleteReplyById(Long id);

}
