package com.example.multimedia.domian;

import com.example.multimedia.domian.enums.Topic;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/20 16:03
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AdminNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;

    private String title;

    private Boolean readed = false;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    private String reason;

    private String reasonContent;

    private String type;

    @CreatedDate
    private Timestamp date;

}
