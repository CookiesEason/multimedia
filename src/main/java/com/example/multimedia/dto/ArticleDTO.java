package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Article;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
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

    private String bgImg;

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

    private Boolean enable;

    private Long commentNum;

    public ArticleDTO(SimpleUserDTO user, Article article,Set<SmallTagDTO> smallTags,Long commentNum) {
        this.id = article.getId();
        this.user = user;
        this.tag = article.getTags().getTag();
        this.title = article.getTitle();
        this.bgImg = article.getBgImg();
        this.text = article.getText();
        this.smallTags = smallTags;
        this.readCount = article.getReadCount();
        this.likeCount = article.getLikeCount();
        this.createDate = article.getCreateDate();
        this.commentNum = commentNum;
        this.enable = article.getEnable();
    }

    public ArticleDTO(SimpleUserDTO user, Article article,Boolean isLike,Set<SmallTagDTO> smallTags,Long commentNum) {
        this.id = article.getId();
        this.user = user;
        this.tag = article.getTags().getTag();
        this.bgImg = article.getBgImg();
        this.title = article.getTitle();
        this.text = article.getText();
        this.smallTags = smallTags;
        this.readCount = article.getReadCount();
        this.likeCount = article.getLikeCount();
        this.isLike = isLike;
        this.commentNum = commentNum;
        this.enable = article.getEnable();
        this.createDate = article.getCreateDate();
    }
}
