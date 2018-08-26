package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Article;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/18 19:14
 */
@Data
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = -4708001306792242840L;

    private Long id;

    private SimpleUserDTO user;

    private String tag;

    private String title;

    private String text;

    private Long readCount;

    private Long likeCount;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Set<SmallTagDTO> smallTags;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Boolean isLike;

    private Timestamp createDate;

    public ArticleDTO(SimpleUserDTO user, Article article,Set<SmallTagDTO> smallTags) {
        this.id = article.getId();
        this.user = user;
        this.tag = article.getTags().getTag();
        this.title = article.getTitle();
        this.text = article.getText();
        this.smallTags = smallTags;
        this.readCount = article.getReadCount();
        this.likeCount = article.getLikeCount();
        this.createDate = article.getCreateDate();
    }

    public ArticleDTO(SimpleUserDTO user, Article article,Boolean isLike,Set<SmallTagDTO> smallTags) {
        this.id = article.getId();
        this.user = user;
        this.tag = article.getTags().getTag();
        this.title = article.getTitle();
        this.text = article.getText();
        this.smallTags = smallTags;
        this.readCount = article.getReadCount();
        this.likeCount = article.getLikeCount();
        this.isLike = isLike;
        this.createDate = article.getCreateDate();
    }
}
