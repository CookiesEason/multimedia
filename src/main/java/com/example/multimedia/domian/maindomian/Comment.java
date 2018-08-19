package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.abstractdomian.AbstractComment;
import com.example.multimedia.domian.enums.Topic;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author CookiesEason
 * 2018/08/05 19:13
 */
@Entity
public class Comment extends AbstractComment {

    private Long topId;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }
}
