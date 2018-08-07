package com.example.multimedia.domian.videodomian;

import com.example.multimedia.domian.abstractdomian.AbstractLike;

import javax.persistence.Entity;

/**
 * @author CookiesEason
 * 2018/08/07 14:53
 */
@Entity
public class VideoLike extends AbstractLike {

    private Long videoId;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
