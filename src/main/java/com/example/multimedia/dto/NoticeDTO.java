package com.example.multimedia.dto;

import lombok.Data;

/**
 * @author CookiesEason
 * 2018/08/14 21:13
 */
@Data
public class NoticeDTO {

    private String username;

    private String url;

    private String content;

    public NoticeDTO(String username, String url, String content) {
        this.username = username;
        this.url = url;
        this.content = content;
    }
}
