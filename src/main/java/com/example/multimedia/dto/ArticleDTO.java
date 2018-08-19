package com.example.multimedia.dto;

import com.example.multimedia.domian.articledomian.Article;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/18 19:14
 */
@Data
public class ArticleDTO {

    private Long id;

    private SimpleUserDTO user;

    private String tag;

    private String title;

    private String text;

    private Long readCount;

    private Long likeCount;

    private Boolean enable;

    private Timestamp createDate;

    public ArticleDTO(SimpleUserDTO user, Article article) {
        this.id = article.getId();
        this.user = user;
        this.tag = article.getTags().getTag();
        this.title = article.getTitle();
        this.text = article.getText();
        this.readCount = article.getReadCount();
        this.likeCount = article.getLikeCount();
        this.enable = article.getEnable();
        this.createDate = article.getCreateDate();
    }
}
