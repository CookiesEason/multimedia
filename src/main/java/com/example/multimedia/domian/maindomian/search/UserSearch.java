package com.example.multimedia.domian.maindomian.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/09/18 11:41
 */
@Document(indexName = "happy_index",type = "user")
@Data
public class UserSearch implements Serializable {

    private static final long serialVersionUID = 6138902945697522255L;

    private Long id;

    private String nickname;

}
