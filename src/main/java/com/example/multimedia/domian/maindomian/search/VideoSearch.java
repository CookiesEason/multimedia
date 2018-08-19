package com.example.multimedia.domian.maindomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/12 12:52
 */
@Document(indexName = "video_index",type = "video")
@Data
public class VideoSearch implements Serializable {

    private static final long serialVersionUID = -7176399469751266972L;

    private Long id;

    private String title;

    private String introduction;

    private String video_url;

    private Long user_id;

    private Long tags_id;

    private Boolean enable;

    private Long like_count;

    private Long play_count;

    private Timestamp create_date;

}
