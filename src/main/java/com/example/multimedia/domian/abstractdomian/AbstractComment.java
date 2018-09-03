package com.example.multimedia.domian.abstractdomian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author CookiesEason
 * 2018/08/05 19:11
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "评论不能为空")
    @Length(max = 500,message = "评论字数不能多余500字")
    private String content;

    private Long fromUid;

    private Long likeCount = 0L;

    @CreatedDate
    private Timestamp createDate;

}
