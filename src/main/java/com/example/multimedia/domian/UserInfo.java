package com.example.multimedia.domian;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author CookiesEason
 * 2018/07/30 10:35
 * 用户信息
 */
@Entity
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 4,max = 14)
    private String nickname;

    private String headImgUrl;

}
