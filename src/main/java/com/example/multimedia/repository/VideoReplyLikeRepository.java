package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.VideoReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 12:54
 */
public interface VideoReplyLikeRepository extends JpaRepository<VideoReplyLike,Long> {

    VideoReplyLike findByUserIdAndReplyId(Long userId, Long replyId);

    Long countAllByReplyIdAndStatus(Long id, boolean status);

    void deleteAllByReplyIdIn(List<Long> ids);

    void deleteAllByReplyId(Long id);

}
