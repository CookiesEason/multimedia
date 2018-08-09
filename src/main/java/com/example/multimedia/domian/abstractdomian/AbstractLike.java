package com.example.multimedia.domian.abstractdomian;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author CookiesEason
 * 2018/08/07 12:31
 */
@MappedSuperclass
@Data
public abstract class AbstractLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private boolean status;

}
