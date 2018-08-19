package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.maindomian.search.VideoSearch;
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

    private long playCount;

    private long likeCount;

    @CreatedDate
    private Timestamp createDate;

    private boolean enable = true;

    @OneToOne(fetch = FetchType.EAGER)
    private Tags tags;


    public Video() {
    }

    public Video(VideoSearch videoSearch, Tags tags) {
        this.id = videoSearch.getId();
        this.userId = videoSearch.getUser_id();
        this.title = videoSearch.getTitle();
        this.introduction = videoSearch.getIntroduction();
        this.videoUrl = videoSearch.getVideo_url();
        this.playCount = videoSearch.getPlay_count();
        this.likeCount = videoSearch.getLike_count();
        this.createDate = videoSearch.getCreate_date();
        this.enable = videoSearch.getEnable();
        this.tags = tags;
    }
}
