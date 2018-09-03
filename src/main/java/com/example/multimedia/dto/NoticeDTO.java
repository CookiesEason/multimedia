package com.example.multimedia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/14 21:13
 */
@Data
public class NoticeDTO implements Serializable {

    private static final long serialVersionUID = -3317768826152076407L;

    private Long messageId;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String nickname;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String headUrl;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String userUrl;

    private String content;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String topicTitle;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String topicUrl;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Long commentId;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String comment;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String reply;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Timestamp date;

    private String type;



    public NoticeDTO(Long messageId,String nickname,String headUrl,String userUrl, String content,Timestamp date,String type) {
        this.messageId = messageId;
        this.nickname = nickname;
        this.headUrl = headUrl;
        this.userUrl = userUrl;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public NoticeDTO(Long messageId, String nickname,String headUrl, String userUrl, String content, String topicTitle, String topicUrl, Timestamp date,String type) {
        this.messageId = messageId;
        this.nickname = nickname;
        this.headUrl = headUrl;
        this.userUrl = userUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.date = date;
        this.type = type;
    }

    public NoticeDTO(Long messageId, String nickname, String headUrl,String userUrl, String content, String topicTitle, String topicUrl, Long commentId, String comment, Timestamp date,String type) {
        this.messageId = messageId;
        this.nickname = nickname;
        this.headUrl = headUrl;
        this.userUrl = userUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.commentId = commentId;
        this.comment = comment;
        this.date = date;
        this.type = type;
    }

    public NoticeDTO(Long messageId, String nickname, String headUrl,String userUrl, String content, String topicTitle, String topicUrl, Long commentId, String comment, String reply, Timestamp date,String type) {
        this.messageId = messageId;
        this.nickname = nickname;
        this.headUrl = headUrl;
        this.userUrl = userUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.commentId = commentId;
        this.comment = comment;
        this.reply = reply;
        this.date = date;
        this.type = type;
    }
}
