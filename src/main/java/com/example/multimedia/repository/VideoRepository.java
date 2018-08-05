package com.example.multimedia.repository;

import com.example.multimedia.domian.Video;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author CookiesEason
 * 2018/08/03 16:24
 */
public interface VideoRepository extends JpaRepository<Video,Long> {

    Video findById(long id);

    void deleteByIdAndUserId(long id,long userId);

    Video findByIdAndUserId(long id,long userId);

}
