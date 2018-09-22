package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author CookiesEason
 * 2018/09/22 19:45
 */
@Data
public class AdminUserDTO {

    private Long id;

    private String role;

    private String nickname;

    private String username;

    private Timestamp timestamp;

    private Boolean enable;

    private Long articleNum;

    private Long videoNum;

    private Long hot;

    private Long follows;

    private Long fans;

    public AdminUserDTO(User user,Long articleNum, Long videoNum, Long hot, Long follows, Long fans) {
        this.id = user.getId();
        this.role = user.getRoleList().get(0).getRole();
        this.nickname = user.getUserInfo().getNickname();
        this.username = user.getUsername();
        this.timestamp = user.getDate();
        this.enable = user.isEnable();
        this.articleNum = articleNum;
        this.videoNum = videoNum;
        this.hot = hot;
        this.follows = follows;
        this.fans = fans;
    }
}
