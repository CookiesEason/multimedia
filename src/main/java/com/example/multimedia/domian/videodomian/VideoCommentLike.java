package com.example.multimedia.domian.videodomian;

import com.example.multimedia.domian.abstractdomian.AbstractLike;

import javax.persistence.Entity;

/**
 * @author CookiesEason
 * 2018/08/07 13:55
 */
@Entity
public class VideoCommentLike extends AbstractLike {

    private Long commentId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
