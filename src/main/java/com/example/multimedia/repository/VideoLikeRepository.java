package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/07 14:54
 */
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {

    VideoLike findByUserIdAndVideoId(Long userId, Long videoId);

    Long countByVideoIdAndStatus(Long videoId, boolean statues);

    void deleteAllByVideoId(Long videoId);
}
