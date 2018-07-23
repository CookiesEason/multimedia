package com.example.multimedia.domian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author CookiesEason
 * 2018/07/23 13:32
 * 用户
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 6,max = 12)
    private String username;

    @Length(min = 6,max = 12)
    private String password;

}
