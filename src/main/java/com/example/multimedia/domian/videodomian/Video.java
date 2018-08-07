package com.example.multimedia.domian.videodomian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "标题不能为空")
    private String title;

    @Length(min = 10,message = "请输入介绍信息不少于10个字")
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
