package com.example.multimedia.domian;

import com.example.multimedia.domian.abstractdomian.AbstractComment;

import javax.persistence.Entity;

/**
 * @author CookiesEason
 * 2018/08/05 19:13
 */
@Entity
public class VideoComment  extends AbstractComment {

    private Long videoId;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
