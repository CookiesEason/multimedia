package com.example.multimedia.dto;

import com.example.multimedia.domian.videodomian.Video;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/07 15:22
 */
@Data
public class VideoDTO {

    private Long videoId;

    private SimpleUserDTO user;

    private String title;

    private String introduction;

    private String videoUrl;

    private Long playCount;

    private Long likeCount;

    private Timestamp createDate;

    private String tag;

    private Boolean isLike;

    private Boolean enable;

    public VideoDTO(SimpleUserDTO user, Video video,Boolean isLike) {
        this.videoId = video.getId();
        this.user = user;
        this.title = video.getTitle();
        this.introduction = video.getIntroduction();
        this.videoUrl = video.getVideoUrl();
        this.playCount = video.getPlayCount();
        this.likeCount = video.getLikeCount();
        this.createDate = video.getCreateDate();
        this.tag = video.getTags().getTag();
        this.enable = video.isEnable();
        this.isLike = isLike;
}

    @Override
    public String toString() {
        return "VideoDTO{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", playCount=" + playCount +
                ", likeCount=" + likeCount +
                ", createDate=" + createDate +
                ", tag='" + tag + '\'' +
                ", enable=" + enable +
                '}';
    }
}
