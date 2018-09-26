package com.example.multimedia.domian.maindomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/21 14:52
 */
@Document(indexName = "happy_index",type = "article")
@Data
public class ArticleSearch implements Serializable {

    private static final long serialVersionUID = -2502846892306950159L;

    private Long id;

    private Long user_id;

    private String tag;

    private String bg_img;

    private String title;

    private String text;

    private Long read_count;

    private Long like_count;

    private Long comment_num;

    private Timestamp create_date;

    private Boolean enable;

}
