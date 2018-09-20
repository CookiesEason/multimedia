package com.example.multimedia.service;

import com.example.multimedia.domian.abstractdomian.AbstractLike;
import com.example.multimedia.domian.enums.Topic;

import java.util.List;

/**
 * 点赞
 * @author CookiesEason
 * 2018/08/07 12:55
 */
public interface LikeService {

    Long like(Long id,Topic topic);

    Long countAllById(Long id);

    void deleteAllByIds(List<Long> ids);

    void deleteAllById(Long id);

    void deleteAllById(Long id,Topic topic);

   AbstractLike status(Long id,Long userId,Topic topic);

}
