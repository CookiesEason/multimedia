package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.Article;
import lombok.Data;

/**
 * @author CookiesEason
 * 2018/09/05 16:26
 */
@Data
public class SimpleArticleDTO {

    private Long articleId;

    private String title;

    private String tag;

    private String imgUrl;

    private Long likeCount;

    public SimpleArticleDTO(Article article) {
        this.articleId = article.getId();
        this.title = article.getTitle();
        this.tag = article.getTags().getTag();
        this.imgUrl = article.getBgImg();
        this.likeCount = article.getLikeCount();
    }
}
