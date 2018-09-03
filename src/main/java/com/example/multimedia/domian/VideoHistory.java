package com.example.multimedia.domian;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/17 15:06
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class VideoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long videoId;

    private String title;

    private Long userId;

    @UpdateTimestamp
    private Timestamp watchTime;

}
