package com.example.multimedia.service;

import com.example.multimedia.domian.User;
import com.example.multimedia.domian.UserInfo;
import com.example.multimedia.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 更新用户个人信息
     * @param userInfo
     * @return
     */
    ResultVo save(UserInfo userInfo, MultipartFile multipartFile);

    /**
     * 用户信息查询
     * @param nickname
     * @return
     */
    User findByUserInfoNickname(String nickname);
}
