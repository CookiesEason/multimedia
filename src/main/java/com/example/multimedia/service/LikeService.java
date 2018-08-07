package com.example.multimedia.service;

/**
 * 点赞
 * @author CookiesEason
 * 2018/08/07 12:55
 */
public interface LikeService {

    void like(Long id);

    Long countAllById(Long id);

}
