package com.example.multimedia.dto;

import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/09/12 10:14
 */
@Data
public class ArticleWorkDTO {

    private ArticleDTO article;

    private List<SimpleArticleDTO> recommendArticles;

    private List<SimpleArticleDTO> hotArticles;

}
