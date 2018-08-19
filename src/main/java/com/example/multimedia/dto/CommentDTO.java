package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.maindomian.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private List<ReplyDTO> replyDTOList;

    public CommentDTO(Comment comment, Long likeCount, User user, List<ReplyDTO> replyDTOList) {
        this.userId = user.getId();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.likeCount = likeCount;
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = comment.getCreateDate();
        this.replyDTOList = replyDTOList;
    }

    public CommentDTO(Comment comment, Long likeCount, User user) {
        this.userId = user.getId();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.likeCount = likeCount;
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = comment.getCreateDate();
    }
}
