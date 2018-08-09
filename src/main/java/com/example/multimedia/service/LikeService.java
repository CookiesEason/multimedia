package com.example.multimedia.service;

import com.example.multimedia.domian.abstractdomian.AbstractLike;
import com.example.multimedia.domian.videodomian.VideoLike;

import java.util.List;

/**
 * 点赞
 * @author CookiesEason
 * 2018/08/07 12:55
 */
public interface LikeService {

    void like(Long id);

    Long countAllById(Long id);

    void deleteAllByIds(List<Long> ids);

    void deleteAllById(Long id);

   AbstractLike status(Long videoId);

}
