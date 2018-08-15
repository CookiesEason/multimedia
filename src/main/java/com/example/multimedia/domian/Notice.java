package com.example.multimedia.domian;

import com.example.multimedia.domian.enums.Topic;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/14 19:43
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Notice implements Serializable {

    private static final long serialVersionUID = 3152688780865392972L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fromUid;

    private Long toUid;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    private Long topicId;

    private Long contentId;

    private Long replyId;

    private String type;

    private Boolean readed = false;

    @CreatedDate
    private Timestamp date;
}
