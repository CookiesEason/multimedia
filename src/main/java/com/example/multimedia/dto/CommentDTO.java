package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.videodomian.VideoComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 21:01
 */
@Data
public class CommentDTO {

    private Long userId;

    private Long commentId;

    private String content;

    private String nickname;

    private String headUrl;

    private Long likeCount;

    private Timestamp createDate;

    @JsonProperty(value = "replyComments")
    private List<ReplyDTO> replyDTOList;

    public CommentDTO(VideoComment videoComment,Long likeCount,User user, List<ReplyDTO> replyDTOList) {
        this.userId = user.getId();
        this.commentId = videoComment.getId();
        this.content = videoComment.getContent();
        this.likeCount = likeCount;
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = videoComment.getDate();
        this.replyDTOList = replyDTOList;
    }
}
