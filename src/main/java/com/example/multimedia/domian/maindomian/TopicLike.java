package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.abstractdomian.AbstractLike;
import com.example.multimedia.domian.enums.Topic;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author CookiesEason
 * 2018/08/07 14:53
 */
@Entity
public class TopicLike extends AbstractLike {

    private Long topId;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
