package com.example.multimedia.domian.maindomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/12 19:04
 */
@Document(indexName = "happy_index",type = "reply")
@Data
public class ReplySearch {

    private Long id;

    private Long commentid;

    private String content;

    private Long fromuid;

    private Timestamp createdate;
}
