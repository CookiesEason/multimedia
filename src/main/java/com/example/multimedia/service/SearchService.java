package com.example.multimedia.service;

import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.dto.ArticleDTO;
import com.example.multimedia.dto.PageDTO;
import com.example.multimedia.dto.VideosDTO;
import com.example.multimedia.vo.ResultVo;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/12 15:41
 */
public interface SearchService {

    /**
     * 搜索用户
     * @param searchContent
     * @return
     */
    ResultVo searchUser(String searchContent);

    /**
     *搜索视频
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    VideosDTO searchVideo(int page, String order, String sort, int startTime, int endTime, String tag,
                          String searchContent, Boolean enable, Boolean auditing);

    /**
     * 搜索文章
     * @param page
     * @param order
     * @param sort
     * @param searchContent
     * @return
     */
    PageDTO<ArticleDTO> searchArticle(int page, String order, String sort, String searchContent, String tag,Boolean enable);

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
