package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.maindomian.search.VideoSearch;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

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

    private String imgUrl;

    private String videoUrl;

    private String fileId;

    private Long time;

    private long playCount = 0L;

    private long likeCount = 0L;

    @CreatedDate
    private Timestamp createDate;

    private boolean auditing = false;

    private boolean enable = true;

    private Long commentNum = 0L;

    @OneToOne(fetch = FetchType.EAGER)
    private Tags tags;

    @ManyToMany
    private Set<SmallTags> smallTags;

    public Video() {
    }

    public Video(VideoSearch videoSearch, Tags tags) {
        this.id = videoSearch.getId();
        this.userId = videoSearch.getUser_id();
        this.title = videoSearch.getTitle();
        this.imgUrl = videoSearch.getImg_url();
        this.introduction = videoSearch.getIntroduction();
        this.videoUrl = videoSearch.getVideo_url();
        this.time = videoSearch.getTime();
        this.playCount = videoSearch.getPlay_count();
        this.likeCount = videoSearch.getLike_count();
        this.createDate = videoSearch.getCreate_date();
        this.enable = videoSearch.getEnable();
        this.tags = tags;
    }
}
