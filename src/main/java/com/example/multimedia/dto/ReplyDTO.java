package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Reply;
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

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Boolean isLike;

    private Long likeCount;

    private SimpleUserDTO fromUser;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private SimpleUserDTO toUser;

    public ReplyDTO(Reply reply, Boolean isLike, Long likeCount, SimpleUserDTO fromUser, SimpleUserDTO toUser) {
        this.replyId = reply.getId();
        this.content = reply.getContent();
        this.createDate = reply.getCreateDate();
        this.isLike = isLike;
        this.likeCount = likeCount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public ReplyDTO(Reply reply, Long likeCount, SimpleUserDTO fromUser) {
        this.replyId = reply.getId();
        this.content = reply.getContent();
        this.createDate = reply.getCreateDate();
        this.likeCount = likeCount;
        this.fromUser = fromUser;
    }

    public ReplyDTO(Reply reply, Long likeCount, SimpleUserDTO fromUser, SimpleUserDTO toUser) {
        this.replyId = reply.getId();
        this.content = reply.getContent();
        this.createDate = reply.getCreateDate();
        this.likeCount = likeCount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
