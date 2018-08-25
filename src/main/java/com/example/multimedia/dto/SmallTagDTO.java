package com.example.multimedia.dto;

import com.example.multimedia.domian.maindomian.tag.SmallTags;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/08/25 21:26
 */
@Data
public class SmallTagDTO implements Serializable {

    private static final long serialVersionUID = -1645164962806145074L;
    private Long id;

    private String smallTag;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String tag;

    public SmallTagDTO(SmallTags smallTags) {
        this.id = smallTags.getId();
        this.smallTag = smallTags.getSmallTag();
    }

    public SmallTagDTO(SmallTags smallTags,String tag) {
        this.id = smallTags.getId();
        this.smallTag = smallTags.getSmallTag();
        this.tag = tag;
    }

}
