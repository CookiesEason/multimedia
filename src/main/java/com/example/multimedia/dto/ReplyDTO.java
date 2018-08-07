package com.example.multimedia.dto;

import com.example.multimedia.domian.videodomian.VideoReply;
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

    private SimpleUserDTO toUser;

    public ReplyDTO(VideoReply videoReply,Long likeCount, SimpleUserDTO fromUser, SimpleUserDTO toUser) {
        this.replyId = videoReply.getId();
        this.content = videoReply.getContent();
        this.createDate = videoReply.getDate();
        this.likeCount = likeCount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
