package com.example.multimedia.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/09/23 10:19
 */
@Data
public class AdminLableDTO implements Serializable {

    private static final long serialVersionUID = 3234150308626910003L;

    private Long id;

    private String smallTag;

    private String tag;

    private Long hot;

    private Long articleNum;

    private Long videoNum;

    private Timestamp createDate;

}
