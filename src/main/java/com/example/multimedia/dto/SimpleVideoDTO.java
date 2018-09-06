package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Video;
import lombok.Data;

/**
 * @author CookiesEason
 * 2018/09/05 16:14
 */
@Data
public class SimpleVideoDTO {

    private Long videoId;

    private String title;

    private String tag;

    private String imgUrl;

    private Long likeCount;

    public SimpleVideoDTO(Video video) {
        this.videoId = video.getId();
        this.title = video.getTitle();
        this.tag = video.getTags().getTag();
        this.imgUrl = video.getImgUrl();
        this.likeCount = video.getLikeCount();
    }
}
