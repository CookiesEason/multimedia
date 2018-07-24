package com.example.multimedia.domian;

import lombok.Data;
import javax.persistence.*;

/**
 * @author CookiesEason
 * 2018/07/24 13:04
 * 用户权限角色
 */
@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

}
