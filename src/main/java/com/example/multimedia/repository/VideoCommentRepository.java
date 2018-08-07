package com.example.multimedia.repository;

import com.example.multimedia.domian.videodomian.VideoComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author CookiesEason
 * 2018/08/05 19:38
 */
public interface VideoCommentRepository extends JpaRepository<VideoComment,Long> {

    VideoComment findVideoCommentById(Long id);

    Page<VideoComment> findAllByVideoId(Pageable pageable, Long videoId);

    Long  deleteByIdAndFromUid(Long id,Long fromUid);

}
