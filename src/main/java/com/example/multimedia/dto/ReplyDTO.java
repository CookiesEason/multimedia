package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/05 20:22
 */
@Data
public class ReplyDTO {

    private Long replyId;

    private SimpleUserDTO fromUser;

    private String content;

    private SimpleUserDTO replyUser;

}
