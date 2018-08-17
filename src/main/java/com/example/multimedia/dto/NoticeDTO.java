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

    private String username;

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
    private Long replyId;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String reply;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Timestamp date;

    public NoticeDTO(Long messageId,String username, String useUrl, String content,Timestamp date) {
        this.messageId = messageId;
        this.username = username;
        this.userUrl = useUrl;
        this.content = content;
        this.date = date;
    }

    public NoticeDTO(Long messageId,String username, String useUrl, String content, String topicTitle, String topicUrl,Timestamp date) {
        this.messageId = messageId;
        this.username = username;
        this.userUrl = useUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.date = date;
    }

    public NoticeDTO(Long messageId,String username, String useUrl, String content, String topicTitle, String topicUrl,Long commentId,String comment,Timestamp date) {
        this.messageId = messageId;
        this.username = username;
        this.userUrl = useUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.commentId = commentId;
        this.comment = comment;
        this.date = date;
    }

    public NoticeDTO(Long messageId,String username, String useUrl, String content, String topicTitle, String topicUrl, Long commentId, Long replyId, String reply,Timestamp date) {
        this.messageId = messageId;
        this.username = username;
        this.userUrl = useUrl;
        this.content = content;
        this.topicTitle = topicTitle;
        this.topicUrl = topicUrl;
        this.commentId = commentId;
        this.replyId = replyId;
        this.reply = reply;
        this.date = date;
    }
}
