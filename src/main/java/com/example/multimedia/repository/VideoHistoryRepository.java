package com.example.multimedia.repository;

import com.example.multimedia.domian.VideoHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/17 15:09
 */
public interface VideoHistoryRepository extends JpaRepository<VideoHistory,Long> {
    VideoHistory findByUserIdAndVideoId(Long userId, Long videoId);

    Page<VideoHistory> findAllByUserId(Long userId, Pageable pageable);

    void deleteAllByWatchTimeBefore(Timestamp timestamp);
}

