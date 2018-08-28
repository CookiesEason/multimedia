package com.example.multimedia.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/08/28 10:35
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @Lob
    @NotBlank(message = "内容不能为空")
    private String text;

    @NotBlank(message = "分类不能为空")
    private String tag;

    @CreatedDate
    private Timestamp createDate;

}
