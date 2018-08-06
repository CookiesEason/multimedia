package com.example.multimedia.domian.abstractdomian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author CookiesEason
 * 2018/08/05 19:11
 */
@MappedSuperclass
@Data
public abstract class AbstractComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "评论不能为空")
    @Length(max = 500,message = "评论字数不能多余500字")
    private String content;

    private Long fromUid;

    private Long likeCount;

    private Long likeUid;

}
