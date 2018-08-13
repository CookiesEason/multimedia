package com.example.multimedia.domian.videodomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/12 19:04
 */
@Document(indexName = "video_index",type = "reply")
@Data
public class VideoReplySearch {

    private Long id;

    private Long comment_id;

    private String content;

    private Long from_uid;

    private Timestamp create_date;
}
