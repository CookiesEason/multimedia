package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.enums.Topic;
import com.example.multimedia.domian.maindomian.Comment;
import com.example.multimedia.domian.maindomian.search.CommentSearch;
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

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Boolean isLike;

    private Long likeCount;

    private Timestamp createDate;

    @JsonProperty(value = "replyComments")
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private List<ReplyDTO> replyDTOList;

    public CommentDTO(Comment comment, Boolean isLike, User user, List<ReplyDTO> replyDTOList) {
        this.userId = user.getId();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = comment.getCreateDate();
        this.isLike = isLike;
        this.replyDTOList = replyDTOList;
    }

    public CommentDTO(Comment comment, User user) {
        this.userId = user.getId();
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = comment.getCreateDate();
    }

    public CommentDTO(CommentSearch commentSearch, User user){
        this.userId = user.getId();
        this.commentId = commentSearch.getId();
        this.content = commentSearch.getContent();
        this.likeCount = commentSearch.getLikecount();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.createDate = commentSearch.getCreatedate();
    }

}
