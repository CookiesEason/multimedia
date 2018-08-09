package com.example.multimedia.dto;

import com.example.multimedia.domian.videodomian.Video;
import lombok.Getter;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/04 14:14
 */
@Getter
public class VideosDTO {

    private List<Video> videoList;

    private Long totalElements;

    private Long totalPages;

    public VideosDTO(List<Video> videoList, Long totalElements, Long totalPages) {
        this.videoList = videoList;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "VideosDTO{" +
                "videoList=" + videoList +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
