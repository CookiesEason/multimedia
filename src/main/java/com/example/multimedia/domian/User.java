package com.example.multimedia.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author CookiesEason
 * 2018/07/23 13:32
 * 用户
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -3684325576403010777L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "请输入正确的邮箱格式")
    @NotBlank(message = "邮箱不能为空")
    private String username;

    @Length(min = 8,message = "密码长度至少为8位")
    private String password;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp date;

    private boolean active = false;

    private boolean enable = true;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private UserInfo userInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> roleList;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
