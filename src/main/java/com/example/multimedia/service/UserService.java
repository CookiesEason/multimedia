package com.example.multimedia.service;

import com.example.multimedia.domian.User;
import com.example.multimedia.vo.ResultVo;

/**
 * @author CookiesEason
 * 2018/07/23 15:16
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return ResultVo
     */
    ResultVo save(User user);

    /**
     * 用户查询
     * @param username
     * @return User
     */
    User findByUsername(String username);
}
