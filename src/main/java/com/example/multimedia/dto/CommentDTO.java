package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.VideoComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 21:01
 */
@Data
public class CommentDTO {

    private Long id;

    private Long commentId;

    private String content;

    private String nickname;

    private String headUrl;

    @JsonProperty(value = "replyComments")
    private List<ReplyDTO> replyDTOList;

    public CommentDTO(VideoComment videoComment, User user, List<ReplyDTO> replyDTOList) {
        this.id = user.getId();
        this.commentId = videoComment.getId();
        this.content = videoComment.getContent();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.replyDTOList = replyDTOList;
    }
}
