package com.example.multimedia.domian;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/14 14:19
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long followerId;

    private Boolean status;

    @LastModifiedDate
    private Timestamp date;

}
