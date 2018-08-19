package com.example.multimedia.domian.maindomian;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/08/02 19:30
 */
@Entity
@Data
public class Tags implements Serializable {

    private static final long serialVersionUID = -6976707651342757455L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tag;

}
