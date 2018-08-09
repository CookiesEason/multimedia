package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
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

    public SimpleUserDTO(User user) {
        this.id = user.getId();
        this.nickname = user.getUserInfo().getNickname();
        this.headUrl = user.getUserInfo().getHeadImgUrl();
    }
}
