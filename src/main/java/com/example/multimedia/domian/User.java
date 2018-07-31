package com.example.multimedia.domian;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

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

    @Length(min = 6,max = 12, message = "用户名长度为6-12位")
    private String username;

    @Length(min = 8,message = "密码长度至少为8位")
    @JsonIgnore
    private String password;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> roleList;

}
