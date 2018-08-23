package com.example.multimedia.service;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/12 15:41
 */
public interface SearchService {

    /**
     *搜索视频
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchVideo(int page,String order,String sort,String searchContent,Boolean enable);

    /**
     * 搜索文章
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @param enable
     * @return
     */
    ResultVo searchArticle(int page,String order,String sort,String searchContent);

    /**
     *搜索评论
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchComment(int page, String order, String sort, String searchContent);

    /**
     * 搜索回复
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    ResultVo searchReply(int page, String order, String sort, String searchContent);

    /**
     * 删除视频
     * @param id
     */
    void  deleteVideoById(Long id);

    /**
     * 删除某主题下的评论
     * @param id
     */
    void deleteAllByTopicId(Long id, Topic topic);

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
