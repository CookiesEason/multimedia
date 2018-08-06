package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.VideoReply;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 20:22
 */
@Data
public class ReplyDTO {

    private Long replyId;

    private String content;

    private Timestamp createDate;

    private SimpleUserDTO fromUser;

    private SimpleUserDTO toUser;

    public ReplyDTO(VideoReply videoReply, SimpleUserDTO fromUser, SimpleUserDTO toUser) {
        this.replyId = videoReply.getId();
        this.content = videoReply.getContent();
        this.createDate = videoReply.getDate();
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
