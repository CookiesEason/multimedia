package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Video;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

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

    private String fileId;

    private String videoUrl;

    private Long time;

    private String imgUrl;

    private Long playCount;

    private Long likeCount;

    private Timestamp createDate;

    private String tag;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Set<SmallTagDTO> smallTags;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Boolean isLike;

    private Boolean enable;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Long commentNum;

    public VideoDTO(SimpleUserDTO user, Video video,Boolean isLike,Set<SmallTagDTO> smallTags) {
        this.videoId = video.getId();
        this.user = user;
        this.title = video.getTitle();
        this.introduction = video.getIntroduction();
        this.fileId = video.getFileId();
        this.videoUrl = video.getVideoUrl();
        this.imgUrl = video.getImgUrl();
        this.time = video.getTime();
        this.playCount = video.getPlayCount();
        this.likeCount = video.getLikeCount();
        this.createDate = video.getCreateDate();
        this.tag = video.getTags().getTag();
        this.smallTags = smallTags;
        this.enable = video.isEnable();
        this.isLike = isLike;
}

    public VideoDTO(SimpleUserDTO user, Video video,Set<SmallTagDTO> smallTags,Long commentNum) {
        this.videoId = video.getId();
        this.user = user;
        this.title = video.getTitle();
        this.introduction = video.getIntroduction();
        this.fileId = video.getFileId();
        this.videoUrl = video.getVideoUrl();
        this.imgUrl = video.getImgUrl();
        this.time = video.getTime();
        this.playCount = video.getPlayCount();
        this.likeCount = video.getLikeCount();
        this.createDate = video.getCreateDate();
        this.tag = video.getTags().getTag();
        this.smallTags = smallTags;
        this.enable = video.isEnable();
        this.commentNum = commentNum;
    }
}
