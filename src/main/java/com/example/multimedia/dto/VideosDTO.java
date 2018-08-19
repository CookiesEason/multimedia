package com.example.multimedia.dto;

import lombok.Getter;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/04 14:14
 */
@Getter
public class VideosDTO {

    private List<VideoDTO> videos;

    private Long totalElements;

    private Long totalPages;

    public VideosDTO(List<VideoDTO> videoDTOS, Long totalElements, Long totalPages) {
        this.videos = videoDTOS;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "VideosDTO{" +
                "videoDTOS=" + videos +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
