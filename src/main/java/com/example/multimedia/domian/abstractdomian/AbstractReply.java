package com.example.multimedia.domian.abstractdomian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author CookiesEason
 * 2018/08/05 19:14
 */
@MappedSuperclass
@Data
public abstract class AbstractReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commentId;

    @NotBlank(message = "评论不能为空")
    @Length(max = 500,message = "评论字数不能多余500字")
    private String content;

    private Long fromUid;

    private Long toUid;

}
