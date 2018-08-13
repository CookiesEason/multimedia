package com.example.multimedia.domian.videodomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/12 16:54
 */
@Data
@Document(indexName = "video_index",type = "comment")
public class VideoCommentSearch implements Serializable {

    private static final long serialVersionUID = -6445216549881349503L;
    private Long id;

    private Long videoid;

    private String content;

    private Long fromuid;

    private Timestamp createdate;
}
