package com.example.multimedia.dto;

import com.example.multimedia.domian.videodomian.VideoReply;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/05 20:22
 */
@Data
public class ReplyDTO {

    private Long replyId;

    private String content;

    private Timestamp createDate;

    private Long likeCount;

    private SimpleUserDTO fromUser;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private SimpleUserDTO toUser;

    public ReplyDTO(VideoReply videoReply,Long likeCount, SimpleUserDTO fromUser, SimpleUserDTO toUser) {
        this.replyId = videoReply.getId();
        this.content = videoReply.getContent();
        this.createDate = videoReply.getCreateDate();
        this.likeCount = likeCount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public ReplyDTO(VideoReply videoReply,Long likeCount, SimpleUserDTO fromUser) {
        this.replyId = videoReply.getId();
        this.content = videoReply.getContent();
        this.createDate = videoReply.getCreateDate();
        this.likeCount = likeCount;
        this.fromUser = fromUser;
    }
}
