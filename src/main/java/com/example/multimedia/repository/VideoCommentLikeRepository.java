package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.VideoCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/07 13:58
 */
public interface VideoCommentLikeRepository extends JpaRepository<VideoCommentLike,Long> {

    VideoCommentLike findByUserIdAndCommentId(Long userId, Long commentId);

    Long countAllBycommentIdAndStatus(Long commentId, boolean status);

}
