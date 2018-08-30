package com.example.multimedia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "MM月dd日")
    private Timestamp watchTime;

   private Long userId;

   private String nickname;

}
