package com.example.multimedia.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/07/30 10:35
 * 用户信息
 */
@Entity
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8540974692377387195L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 4,max = 20)
    private String nickname;

    private String headImgUrl;

    private String signature;

    private String introduction;

    private String announcement;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp birthday;

    private String sex = "保密";

    private String province;

    private String city;

    private String zone;

}
