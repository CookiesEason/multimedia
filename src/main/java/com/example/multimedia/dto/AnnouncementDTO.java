package com.example.multimedia.dto;

import com.example.multimedia.domian.Announcement;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/28 11:54
 */
@Data
public class AnnouncementDTO implements Serializable {

    private Long id;

    private String title;

    private String tag;

    @JsonFormat(pattern = "MM/dd")
    private Timestamp createDate;

    public AnnouncementDTO(Announcement announcement) {
        this.id = announcement.getId();
        this.title = announcement.getTitle();
        this.tag = announcement.getTag();
        this.createDate = announcement.getCreateDate();
    }
}
