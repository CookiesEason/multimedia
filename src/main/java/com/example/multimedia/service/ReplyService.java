package com.example.multimedia.service;

import com.example.multimedia.domian.videodomian.VideoReply;
import com.example.multimedia.vo.ResultVo;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 19:55
 */
public interface ReplyService {

    VideoReply findById(Long id);


    /**
     * 回复
     * @param commentId
     * @param content
     * @param toUid
     * @return
     */
    @PreAuthorize(value = "hasRole('ROLE_SENIOR_USER') or hasRole('ADMIN')")
    ResultVo reply(Long commentId,String content,Long toUid);

    /**
     * 查找某条评论下的所有回复
     * @param id
     * @return
     */
    List<VideoReply> findAllByCommentId(Long id);

    /**
     * 删除回复
     * @param id
     */
    void deleteById(Long id);

    /**
     * 删除某评论下所有回复
     */
    void deleteAllByCommentId(Long commentId);

    /**
     * 批量删除
     */
    void deleteAllByCommentIdIn(List<Long> ids);
}
