package com.example.multimedia.domian;

import lombok.Data;

import javax.persistence.*;

/**
 * @author CookiesEason
 * 2018/08/02 19:35
 */
@Entity
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String videoUrl;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private Tags tags;

}
