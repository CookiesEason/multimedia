package com.example.multimedia.domian.maindomian;

import com.example.multimedia.domian.maindomian.search.ArticleSearch;
import com.example.multimedia.domian.maindomian.tag.SmallTags;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 16:58
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(fetch = FetchType.EAGER)
    private Tags tags;

    @NotBlank(message = "标题不能为空")
    private String title;

    @Lob
    @NotBlank(message = "内容不能为空")
    private String text;

    @NotBlank(message = "封面不能为空")
    private String bgImg;

    @ManyToMany
    private Set<SmallTags> smallTags;

    private Long readCount = 0L;

    private Long likeCount = 0L;

    private Boolean enable = true;

    private Long commentNum = 0L;

    @CreatedDate
    private Timestamp createDate;


    public Article() {
    }

    public Article(ArticleSearch articleSearch, Tags tags) {
        this.id = articleSearch.getId();
        this.userId = articleSearch.getUser_id();
        this.tags = tags;
        this.bgImg = articleSearch.getBg_img();
        this.title = articleSearch.getTitle();
        this.text = articleSearch.getText();
        this.readCount = articleSearch.getRead_count();
        this.likeCount = articleSearch.getLike_count();
        this.createDate = articleSearch.getCreate_date();
    }
}
