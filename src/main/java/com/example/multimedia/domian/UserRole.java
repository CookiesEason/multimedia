package com.example.multimedia.domian;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author CookiesEason
 * 2018/07/24 13:04
 * 用户权限角色
 */
@Entity
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8577944768414457825L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String role;

}
