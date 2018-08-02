package com.example.multimedia.dto;

import com.example.multimedia.domian.User;
import lombok.Data;

import java.util.List;

/**
 * @author CookiesEason
 * 2018/08/02 20:03
 */
@Data
public class UsersDTO {

    List<User> users;

    private Integer totalElements;

    private Integer totalPages;

    public UsersDTO(List<User> users,Integer totalElements,Integer totalPages) {
        this.users = users;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
