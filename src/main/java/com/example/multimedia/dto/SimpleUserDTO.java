package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author CookiesEason
 * 2018/08/05 20:47
 */
@Data
public class SimpleUserDTO {

    private Long id;

    private String nickname;

    private String headUrl;

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Long hot;

    public SimpleUserDTO(User user) {
        this.id = user.getId();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
    }

    public SimpleUserDTO(User user,Long hot) {
        this.id = user.getId();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
        this.hot = hot;
    }
}
