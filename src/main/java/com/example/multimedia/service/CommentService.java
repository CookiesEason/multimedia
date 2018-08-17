package com.example.multimedia.service;

import com.example.multimedia.domian.abstractdomian.AbstractComment;
import com.example.multimedia.vo.ResultVo;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author CookiesEason
 * 2018/08/05 19:36
 */
public interface CommentService {

    /**
     * 创建评论
     * @param videoId
     * @param content
     * @return
     */
    @PreAuthorize(value = "hasRole('ROLE_SENIOR_USER') or hasRole('ADMIN')")
    ResultVo createComment(Long videoId,String content);

    /**
     * 获取某个视频下所有评论,分页
     * @param videoId
     * @param commentPage
     * @return
     */
    ResultVo getComments(Long videoId,int commentPage);

    /**
     * 查找评论
     * @param id
     * @return
     */
    AbstractComment findById(Long id);

    /**
     * User
     * 删除自己评论
     * @param id
     */
    void deleteById(Long id);

    /**
     * 删除所有主题评论
     * @param contentId
     */
    void deleteAllBycontentId(Long contentId);

    /**
     * 获取所有评论
     * @param page
     * @param size
     * @param order
     * @param sort
     * @return
     */
    ResultVo findAll(int page,int size,String order,String sort);

}
