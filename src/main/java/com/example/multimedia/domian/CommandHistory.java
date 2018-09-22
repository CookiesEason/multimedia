package com.example.multimedia.domian;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/09/22 18:06
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
public class CommandHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String type;

    private String command;

    private String people;

   @CreatedDate
    private Timestamp createDate;

}
