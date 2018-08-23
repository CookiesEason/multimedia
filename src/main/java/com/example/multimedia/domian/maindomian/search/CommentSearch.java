package com.example.multimedia.domian.maindomian.search;

import com.example.multimedia.domian.enums.Topic;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/12 16:54
 */
@Data
@Document(indexName = "happy_index",type = "comment")
public class CommentSearch implements Serializable {

    private static final long serialVersionUID = -6445216549881349503L;
    private Long id;

    private Long topid;

    private String content;

    private Long fromuid;

    private Long likecount;

    private Timestamp createdate;

    @Enumerated(EnumType.STRING)
    private Topic topic;
}
