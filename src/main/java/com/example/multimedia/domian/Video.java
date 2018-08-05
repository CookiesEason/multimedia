package com.example.multimedia.domian;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/02 19:35
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private String title;

    private String introduction;

    private String videoUrl;

    private long likeCount;

    private long playCount;

    @CreatedDate
    private Timestamp createDate;

    private boolean enable = false;

    @OneToOne(fetch = FetchType.EAGER)
    private Tags tags;
}
