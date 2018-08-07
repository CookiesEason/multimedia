package com.example.multimedia.dto;

import com.example.multimedia.domian.videodomian.Video;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/07 15:22
 */
@Data
public class VideoDTO {

    private SimpleUserDTO user;

    private String title;

    private String introduction;

    private String videoUrl;

    private Long playCount;

    private Long likeCount;

    private Timestamp createDate;

    private String tag;

    private Boolean enable;

    public VideoDTO(SimpleUserDTO user, Video video,Long likeCount) {
        this.user = user;
        this.title = video.getTitle();
        this.introduction = video.getIntroduction();
        this.videoUrl = video.getIntroduction();
        this.playCount = video.getPlayCount();
        this.likeCount = likeCount;
        this.createDate = video.getCreateDate();
        this.tag = video.getTags().getTag();
        this.enable = video.isEnable();
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
