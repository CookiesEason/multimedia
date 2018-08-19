package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.abstractdomian.AbstractLike;

import javax.persistence.Entity;

/**
 * @author CookiesEason
 * 2018/08/07 12:50
 */
@Entity
public class ReplyLike extends AbstractLike {

    private Long replyId;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }
}
