package com.example.multimedia.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/17 15:41
 */
@Data
public class VideoHistoryDTO {

    private Long videoId;

    private String title;

    private Timestamp watchTime;

}
