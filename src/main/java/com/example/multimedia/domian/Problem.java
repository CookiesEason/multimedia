package com.example.multimedia.domian;

import com.example.multimedia.domian.enums.Topic;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/09/10 19:06
 */
@Entity
@Data
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;

    @Enumerated(value = EnumType.STRING)
    private Topic topic;

    private String reasons;

}
