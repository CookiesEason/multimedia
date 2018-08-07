package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.VideoReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 20:03
 */
public interface VideoReplyRepository extends JpaRepository<VideoReply,Long> {

    List<VideoReply> findAllByCommentId(Long commentId);

    List<VideoReply> deleteAllByCommentId(Long id);

    void deleteByIdAndFromUid(Long id,Long fromId);

    List<VideoReply> deleteAllByCommentIdIn(List <Long> ids);
}
