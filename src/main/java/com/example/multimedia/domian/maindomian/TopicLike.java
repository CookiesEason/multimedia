package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.abstractdomian.AbstractLike;
import com.example.multimedia.domian.enums.Topic;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/07 14:53
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TopicLike extends AbstractLike {

    private Long topId;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    @LastModifiedDate
    private Timestamp date;

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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
