package com.example.multimedia.dto;

import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/09/05 16:12
 */
@Data
public class VideoWorkDTO {

    private VideoDTO video;

    private List<SimpleVideoDTO> otherVideos;

    private List<SimpleArticleDTO> otherArticles;

    private List<SimpleVideoDTO> recommendVideos;

    private List<SimpleVideoDTO> relatedVideos;

}
